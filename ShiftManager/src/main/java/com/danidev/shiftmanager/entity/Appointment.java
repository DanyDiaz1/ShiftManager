package com.danidev.shiftmanager.entity;


import com.danidev.shiftmanager.entity.enums.Status;

import java.time.LocalDateTime;

public class Appointment {
    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private User user;
    private Service service;
}
