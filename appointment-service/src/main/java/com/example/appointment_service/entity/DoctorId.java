package com.example.appointment_service.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Composite primary key class for {@link Doctor} entity.
 *
 * <p>
 * This class represents the composite key consisting of:
 * </p>
 *
 * <ul>
 *     <li>doctorName</li>
 *     <li>availabilityDate</li>
 * </ul>
 *
 * <p>
 * It is used with {@link jakarta.persistence.IdClass} in the
 * {@link Doctor} entity to uniquely identify a doctor's availability
 * for a specific date.
 * </p>
 *
 * <p>
 * Requirements for composite key class:
 * </p>
 * <ul>
 *     <li>Must implement {@link Serializable}</li>
 *     <li>Must define same field names as in the entity</li>
 *     <li>Must override equals() and hashCode()</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorId implements Serializable {

    /**
     * Name of the doctor.
     */
    private String doctorName;

    /**
     * Availability date of the doctor.
     */
    private LocalDate availabilityDate;
}
