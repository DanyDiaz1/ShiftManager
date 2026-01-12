package com.danidev.shiftmanager.appointment.controller;

import com.danidev.shiftmanager.appointment.dto.AppointmentRequest;
import com.danidev.shiftmanager.appointment.dto.DailyAppointmentResponse;
import com.danidev.shiftmanager.appointment.entity.Appointment;
import com.danidev.shiftmanager.appointment.mapper.AppointmentMapper;
import com.danidev.shiftmanager.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@PreAuthorize("isAuthenticated()")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> create(
            @Valid @RequestBody AppointmentRequest request) {

        Appointment appointment = appointmentService.create(
                request.date,
                request.startTime,
                request.serviceId,
                request.clientName,
                request.clientEmail
        );

        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<DailyAppointmentResponse>> getDailyAgenda(
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(
                appointmentService.getDailyAgenda(date)
                        .stream()
                        .map(AppointmentMapper::toDailyResponse)
                        .toList()
        );
    }

    @GetMapping("/available")
    public ResponseEntity<List<LocalTime>> getAvailableSlots(
            @RequestParam LocalDate date,
            @RequestParam Long serviceId) {

        return ResponseEntity.ok(
                appointmentService.getAvailableSlots(date, serviceId)
        );
    }

}
