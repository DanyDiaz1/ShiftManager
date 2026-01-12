package com.danidev.shiftmanager.appointment.service;

import com.danidev.shiftmanager.appointment.dto.AppointmentRequest;
import com.danidev.shiftmanager.appointment.entity.Appointment;
import com.danidev.shiftmanager.appointment.repository.AppointmentRepository;
import com.danidev.shiftmanager.exception.BusinessException;
import com.danidev.shiftmanager.service.entity.ServiceEntity;
import com.danidev.shiftmanager.service.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(18, 0);

    private final AppointmentRepository repository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository repository,
                              ServiceRepository serviceRepository) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
    }

    public Appointment create(AppointmentRequest request) {

        validateNotInPast(request.getDate(), request.getStartTime());

        ServiceEntity service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new BusinessException("Service not found"));

        LocalTime endTime = request.getStartTime()
                .plusMinutes(service.getDurationMinutes());

        if (request.getStartTime().isBefore(OPEN_TIME) || endTime.isAfter(CLOSE_TIME)) {
            throw new BusinessException("Outside business hours");
        }

        boolean hasConflicts = !repository
                .findConflictingAppointments(
                        request.getDate(),
                        request.getStartTime(),
                        endTime
                )
                .isEmpty();

        if (hasConflicts) {
            throw new BusinessException("Time slot not available");
        }

        Appointment appointment = new Appointment();
        appointment.setDate(request.getDate());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(endTime);
        appointment.setService(service);
        appointment.setClientName(request.getClientName());
        appointment.setClientEmail(request.getClientEmail());

        return repository.save(appointment);
    }

    private void validateNotInPast(LocalDate date, LocalTime startTime) {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (date.isBefore(today)) {
            throw new BusinessException("Cannot create appointments in the past");
        }
        System.out.printf("hola");
        if (date.isEqual(today) && startTime.isBefore(now)) {
            throw new BusinessException("Cannot create an appointment before the current time");
        }
    }

    public List<Appointment> getDailyAgenda(LocalDate date) {
        return repository.findByDateOrderByStartTimeAsc(date);
    }

    public List<LocalTime> getAvailableSlots(
            LocalDate date,
            Long serviceId) {

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException("Service not found"));

        int duration = service.getDurationMinutes();

        List<Appointment> appointments =
                repository.findByDateOrderByStartTimeAsc(date);

        List<LocalTime> availableSlots = new ArrayList<>();

        LocalTime time = OPEN_TIME;

        while (time.plusMinutes(duration).isBefore(CLOSE_TIME.plusSeconds(1))) {

            LocalTime slotEnd = time.plusMinutes(duration);

            LocalTime finalTime = time;
            boolean overlaps = appointments.stream().anyMatch(a ->
                    finalTime.isBefore(a.getEndTime()) &&
                            slotEnd.isAfter(a.getStartTime())
            );

            if (!overlaps) {
                availableSlots.add(time);
            }

            time = time.plusMinutes(15); // intervalo configurable
        }

        return availableSlots;
    }
}
