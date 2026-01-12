package com.danidev.shiftmanager.appointment.mapper;

import com.danidev.shiftmanager.appointment.dto.DailyAppointmentResponse;
import com.danidev.shiftmanager.appointment.entity.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public static DailyAppointmentResponse toDailyResponse(Appointment a) {
        DailyAppointmentResponse dto = new DailyAppointmentResponse();
        dto.setId(a.getId());
        dto.setStartTime(a.getStartTime());
        dto.setEndTime(a.getEndTime());
        dto.setClientName(a.getClientName());
        dto.setServiceName(a.getService().getName());
        return dto;
    }
}