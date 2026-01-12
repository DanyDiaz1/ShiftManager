package com.danidev.shiftmanager.appointment.service;

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
        ServiceEntity service = new ServiceEntity();
        service.setId(1L);
        service.setDurationMinutes(30);

        when(serviceRepository.findById(1L))
                .thenReturn(Optional.of(service));

        when(appointmentRepository.save(any(Appointment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Appointment result = appointmentService.create(
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                1L,
                "Juan",
                "juan@mail.com"
        );

        assertNotNull(result);
        assertEquals(LocalTime.of(10, 30), result.getEndTime());
    }

    @Test
    void shouldFailWhenTimeSlotIsNotAvailable() {

        ServiceEntity service = new ServiceEntity();
        service.setId(1L);
        service.setDurationMinutes(30);

        when(serviceRepository.findById(1L))
                .thenReturn(Optional.of(service));

        when(appointmentRepository.findConflictingAppointments(
                any(LocalDate.class),
                any(LocalTime.class),
                any(LocalTime.class)
        )).thenReturn(List.of(new Appointment()));

        assertThrows(BusinessException.class, () ->
                appointmentService.create(
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        1L,
                        "Juan",
                        "juan@mail.com"
                )
        );
    }
}