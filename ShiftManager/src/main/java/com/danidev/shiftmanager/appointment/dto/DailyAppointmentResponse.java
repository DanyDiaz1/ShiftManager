package com.danidev.shiftmanager.appointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class DailyAppointmentResponse {

    public Long id;
    public LocalTime startTime;
    public LocalTime endTime;
    public String clientName;
    public String serviceName;
}
