package com.example.appointment_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity representing a Doctor's availability for appointments.
 *
 * <p>
 * This class maps to the <b>doctors</b> table in the database.
 * It uses a composite primary key consisting of:
 * </p>
 *
 * <ul>
 *     <li>doctorName</li>
 *     <li>availabilityDate</li>
 * </ul>
 *
 * <p>
 * The composite key is defined using {@link IdClass} with {@link DoctorId}.
 * </p>
 *
 * <p>
 * It stores total appointment slots and remaining available slots
 * for a doctor on a specific date.
 * </p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "doctors")
@IdClass(DoctorId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    /**
     * Name of the doctor.
     * Part of composite primary key.
     */
    @Id
    private String doctorName;

    /**
     * Date on which the doctor is available.
     * Part of composite primary key.
     */
    @Id
    private LocalDate availabilityDate;

    /**
     * Total number of appointment slots for the day.
     */
    private int totalAppointments;

    /**
     * Remaining available appointment slots.
     */
    private int remainingAppointments;
}
