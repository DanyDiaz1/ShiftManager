package com.danidev.shiftmanager.appointment.controller;

import com.danidev.shiftmanager.appointment.dto.AppointmentRequest;
import com.danidev.shiftmanager.appointment.entity.Appointment;
import com.danidev.shiftmanager.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

}
