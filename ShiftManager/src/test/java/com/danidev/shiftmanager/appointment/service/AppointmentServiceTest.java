package com.danidev.shiftmanager.appointment.service;

import com.danidev.shiftmanager.appointment.dto.AppointmentRequest;
import com.danidev.shiftmanager.appointment.entity.Appointment;
import com.danidev.shiftmanager.appointment.repository.AppointmentRepository;
import com.danidev.shiftmanager.exception.BusinessException;
import com.danidev.shiftmanager.service.entity.ServiceEntity;
import com.danidev.shiftmanager.service.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private AppointmentService appointmentService;


    @Test
    void shouldCreateAppointmentSuccessfully() {

        // Arrange
        ServiceEntity service = new ServiceEntity();
        service.setId(1L);
        service.setDurationMinutes(30);

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(10, 0));
        request.setServiceId(1L);
        request.setClientName("Juan Perez");
        request.setClientEmail("juan@mail.com");

        when(serviceRepository.findById(1L))
                .thenReturn(Optional.of(service));

        when(appointmentRepository.findConflictingAppointments(
                any(LocalDate.class),
                any(LocalTime.class),
                any(LocalTime.class)
        )).thenReturn(List.of());

        when(appointmentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Appointment result = appointmentService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(request.getDate(), result.getDate());
        assertEquals(request.getStartTime(), result.getStartTime());
        assertEquals("Juan Perez", result.getClientName());
    }

    @Test
    void shouldFailWhenServiceDoesNotExist() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(10, 0));
        request.setServiceId(99L);
        request.setClientName("Juan");
        request.setClientEmail("juan@mail.com");

        when(serviceRepository.findById(99L))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> appointmentService.create(request)
        );

        assertEquals("Service not found", exception.getMessage());
    }

    @Test
    void shouldFailWhenDateIsInThePast() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now().minusDays(1));
        request.setStartTime(LocalTime.of(10, 0));
        request.setServiceId(1L);
        request.setClientName("Juan");
        request.setClientEmail("juan@mail.com");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> appointmentService.create(request)
        );

        assertEquals("Cannot create appointments in the past", exception.getMessage());
    }

    @Test
    void shouldFailWhenAppointmentIsTodayButTimeIsInThePast() {

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now());
        request.setStartTime(LocalTime.now().minusMinutes(30));
        request.setServiceId(1L);
        request.setClientName("Juan");
        request.setClientEmail("juan@mail.com");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> appointmentService.create(request)
        );

        assertEquals(
                "Cannot create an appointment before the current time",
                exception.getMessage()
        );
    }

    @Test
    void shouldFailWhenOutsideBusinessHours() {

        ServiceEntity service = new ServiceEntity();
        service.setId(1L);
        service.setDurationMinutes(60);

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(7, 0)); // antes de abrir
        request.setServiceId(1L);
        request.setClientName("Juan");
        request.setClientEmail("juan@mail.com");

        when(serviceRepository.findById(1L))
                .thenReturn(Optional.of(service));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> appointmentService.create(request)
        );

        assertEquals("Outside business hours", exception.getMessage());
    }

    @Test
    void shouldFailWhenTimeSlotIsNotAvailable() {

        ServiceEntity service = new ServiceEntity();
        service.setId(1L);
        service.setDurationMinutes(30);

        AppointmentRequest request = new AppointmentRequest();
        request.setDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(10, 0));
        request.setServiceId(1L);
        request.setClientName("Juan");
        request.setClientEmail("juan@mail.com");

        when(serviceRepository.findById(1L))
                .thenReturn(Optional.of(service));

        when(appointmentRepository.findConflictingAppointments(
                any(LocalDate.class),
                any(LocalTime.class),
                any(LocalTime.class)
        )).thenReturn(List.of(new Appointment()));

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> appointmentService.create(request)
        );

        assertEquals("Time slot not available", exception.getMessage());
    }

}