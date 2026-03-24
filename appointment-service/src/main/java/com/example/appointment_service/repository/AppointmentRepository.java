package com.example.appointment_service.repository;

import com.example.appointment_service.entity.Appointment;
import com.example.appointment_service.dto.TodayAppointmentResponse;
import com.example.appointment_service.entity.Doctor;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Repository interface for managing {@link Appointment} entities.
 *
 * <p>
 * Extends {@link JpaRepository} to provide built-in CRUD operations.
 * Custom query methods are defined using Spring Data JPA
 * method naming conventions and JPQL queries.
 * </p>
 *
 * <p>
 * Some methods return {@link TodayAppointmentResponse} DTOs
 * using constructor-based projections for optimized queries.
 * </p>
 *
 * @since 1.0
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Finds an appointment by doctor name, appointment date,
     * and appointment time.
     *
     * @param doctorName doctor name
     * @param appointmentDate appointment date
     * @param appointmentTime appointment time
     * @return Optional containing appointment if found
     */
    Optional<Appointment>
    findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTimeAndStatusIn(
            String doctorName,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            List<String> statuses
    );

    Optional<Appointment> findByAppointmentId(Long appointmentId);
    /**
     * Finds an appointment by patient name (case insensitive).
     *
     * @param patientName patient name
     * @return Optional containing appointment if found
     */
    Optional<Appointment> findByPatientNameIgnoreCase(String patientName);

    /**
     * Retrieves today's appointments.
     *
     * Uses constructor-based projection to return
     * {@link TodayAppointmentResponse}.
     *
     * @return list of today's appointments
     */
    @Query("""
        SELECT new com.example.appointment_service.dto.TodayAppointmentResponse(
            a.doctor.doctorName,
            a.patientName,
            a.appointmentDate,
            a.appointmentTime,
            a.status
        )
        FROM Appointment a
        WHERE a.appointmentDate = CURRENT_DATE
    """)
    List<TodayAppointmentResponse> findTodayAppointments();

    /**
     * Retrieves all currently booked or rescheduled appointments.
     *
     * @return list of booked appointments
     */
    @Query("""
        SELECT new com.example.appointment_service.dto.TodayAppointmentResponse(
            a.doctor.doctorName,
            a.patientName,
            a.appointmentDate,
            a.appointmentTime,
            a.status
        )
        FROM Appointment a
        WHERE a.status IN ('BOOKED','RESCHEDULED')
    """)
    List<TodayAppointmentResponse> findBookedAppointments();

    /**
     * Retrieves booked or rescheduled appointments for a specific date.
     *
     * @param date appointment date
     * @return list of appointments for the given date
     */
    @Query("""
        SELECT new com.example.appointment_service.dto.TodayAppointmentResponse(
            a.doctor.doctorName,
            a.patientName,
            a.appointmentDate,
            a.appointmentTime,
            a.status
        )
        FROM Appointment a
        WHERE a.status IN ('BOOKED','RESCHEDULED')
        AND a.appointmentDate = :date
    """)
    List<TodayAppointmentResponse> findBookedAppointmentsByDate(
            @Param("date") LocalDate date
    );

    /**
     * Retrieves upcoming booked or rescheduled appointments
     * between two dates.
     *
     * @param fromDate start date
     * @param toDate end date
     * @return list of upcoming appointments
     */
    @Query("""
        SELECT new com.example.appointment_service.dto.TodayAppointmentResponse(
            a.doctor.doctorName,
            a.patientName,
            a.appointmentDate,
            a.appointmentTime,
            a.status
        )
        FROM Appointment a
        WHERE a.appointmentDate BETWEEN :fromDate AND :toDate
          AND a.status IN ('BOOKED', 'RESCHEDULED')
    """)
    List<TodayAppointmentResponse> findUpcomingAppointments(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
    List<Appointment> findByDoctorIn(List<Doctor> doctors);
}
