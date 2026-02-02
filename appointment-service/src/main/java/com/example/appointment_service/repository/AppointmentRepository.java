package com.example.appointment_service.repository;

import com.example.appointment_service.entity.Appointment;
import com.example.appointment_service.dto.TodayAppointmentResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment>
    findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
            String doctorName,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    Optional<Appointment> findByPatientNameIgnoreCase(String patientName);

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

//    @Query("""
//    SELECT new com.example.appointment_service.dto.TodayAppointmentResponse(
//        a.doctor.doctorName,
//        a.patientName,
//        a.appointmentTime,
//        a.status
//    )
//    FROM Appointment a
//    WHERE
//        (a.appointmentDate > :fromDate
//         OR (a.appointmentDate = :fromDate AND a.appointmentTime >= :fromTime))
//    AND
//        (a.appointmentDate < :toDate
//         OR (a.appointmentDate = :toDate AND a.appointmentTime <= :toTime))
//    AND a.status IN ('BOOKED', 'RESCHEDULED')
//""")
//    List<TodayAppointmentResponse> findUpcomingAppointments(
//            @Param("fromDate") LocalDate fromDate,
//            @Param("fromTime") LocalTime fromTime,
//            @Param("toDate") LocalDate toDate,
//            @Param("toTime") LocalTime toTime
//    );

//    @Modifying
//    @Transactional
//    @Query("""
//    DELETE FROM Appointment a
//    WHERE a.appointmentDateTime < :now
//""")
//    void deleteExpired(@Param("now") LocalDateTime now);


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
}