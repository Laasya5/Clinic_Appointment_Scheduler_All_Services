package com.example.patient_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing a Patient in the system.
 *
 * <p>
 * This class maps to the <b>patients</b> table in the database.
 * It stores personal information, medical history, and contact details.
 * </p>
 *
 * <p>
 * Collections such as chronic conditions, previous illnesses,
 * and current medications are stored using {@link ElementCollection}.
 * </p>
 *
 * @since 1.0
 */
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    /**
     * Unique identifier for the patient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Full name of the patient.
     */
    private String name;

    /**
     * Age of the patient.
     */
    private int age;

    /**
     * Gender of the patient.
     */
    private String gender;

    /**
     * Blood group of the patient.
     */
    private String bloodGroup;

    /**
     * List of chronic medical conditions.
     */
    @ElementCollection
    private List<String> chronicConditions;

    /**
     * List of previously diagnosed illnesses.
     */
    @ElementCollection
    private List<String> previousIllnesses;

    /**
     * List of current medications being taken.
     */
    @ElementCollection
    private List<String> currentMedications;

    /**
     * Contact phone number.
     */
    private String phoneNumber;

    /**
     * Email address of the patient.
     */
    private String email;

    /**
     * Residential address of the patient.
     */
    private String address;
}
