package com.danidev.shiftmanager.appointment.repository;

import com.danidev.shiftmanager.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.*;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        SELECT a FROM Appointment a
        WHERE a.date = :date
        AND (
            :start < a.endTime AND :end > a.startTime
        )
    """)
    List<Appointment> findConflictingAppointments(
            @Param("date") LocalDate date,
            @Param("start") LocalTime start,
            @Param("end") LocalTime end
    );

    List<Appointment> findByDateOrderByStartTimeAsc(LocalDate date);
}
