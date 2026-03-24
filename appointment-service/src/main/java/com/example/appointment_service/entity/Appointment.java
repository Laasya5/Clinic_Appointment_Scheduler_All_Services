package com.example.appointment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity representing an appointment between a patient and a doctor.
 *
 * <p>
 * This class maps to the <b>appointments</b> table in the database.
 * It stores appointment details including patient name,
 * date, time, status, and associated doctor.
 * </p>
 *
 * <p>
 * Each appointment is linked to a {@link Doctor} entity
 * using a composite foreign key (doctor name and availability date).
 * </p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    /**
     * Unique identifier for the appointment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;

    /**
     * Name of the patient.
     */
    private String patientName;

    /**
     * Date of the appointment.
     */
    private LocalDate appointmentDate;

    /**
     * Time of the appointment.
     */
    private LocalTime appointmentTime;

    /**
     * Current status of the appointment
     * (e.g., BOOKED, CANCELLED, COMPLETED).
     */
    private String status;

    /**
     * Associated doctor for the appointment.
     *
     * <p>
     * Uses composite foreign key:
     * doctor_name + availability_date.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "doctor_name", referencedColumnName = "doctorName")
    @JoinColumn(name = "availability_date", referencedColumnName = "availabilityDate")
    private Doctor doctor;
}
