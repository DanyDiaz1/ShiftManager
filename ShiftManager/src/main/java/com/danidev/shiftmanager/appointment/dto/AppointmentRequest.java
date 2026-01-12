package com.danidev.shiftmanager.appointment.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.*;

@Getter @Setter
public class AppointmentRequest {

    @NotNull
    @FutureOrPresent(message = "Date must be today or in the future")
    public LocalDate date;

    @NotNull
    public LocalTime startTime;

    @NotNull
    public Long serviceId;

    @NotBlank
    public String clientName;

    @Email
    @NotBlank
    public String clientEmail;
}
