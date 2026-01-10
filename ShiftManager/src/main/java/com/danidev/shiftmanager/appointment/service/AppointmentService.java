package com.danidev.shiftmanager.appointment.service;

import com.danidev.shiftmanager.appointment.entity.Appointment;
import com.danidev.shiftmanager.appointment.repository.AppointmentRepository;
import com.danidev.shiftmanager.exception.BusinessException;
import com.danidev.shiftmanager.service.entity.ServiceEntity;
import com.danidev.shiftmanager.service.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.time.*;

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

    public Appointment create(LocalDate date,
                              LocalTime startTime,
                              Long serviceId,
                              String clientName,
                              String clientEmail) {

        if (date.isBefore(LocalDate.now())) {
            throw new BusinessException("Cannot create appointments in the past");
        }

        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new BusinessException("Service not found"));

        LocalTime endTime = startTime.plusMinutes(service.getDurationMinutes());

        if (startTime.isBefore(OPEN_TIME) || endTime.isAfter(CLOSE_TIME)) {
            throw new BusinessException("Outside business hours");
        }

        boolean hasConflicts = !repository
                .findConflictingAppointments(date, startTime, endTime)
                .isEmpty();

        if (hasConflicts) {
            throw new BusinessException("Time slot not available");
        }

        Appointment appointment = new Appointment();
        appointment.setDate(date);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setService(service);
        appointment.setClientName(clientName);
        appointment.setClientEmail(clientEmail);

        return repository.save(appointment);
    }
}
