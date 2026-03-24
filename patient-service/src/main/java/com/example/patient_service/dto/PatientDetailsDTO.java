package com.example.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) used to transfer patient details
 * between client and server.
 *
 * <p>
 * This DTO is used during create and update operations
 * to capture patient-related information from API requests.
 * </p>
 *
 * <p>
 * It separates the external API layer from the internal
 * {@link com.example.patient_service.entity.Patient} entity.
 * </p>
 *
 * @since 1.0
 */
@Data
public class PatientDetailsDTO {

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
    private List<String> chronicConditions;

    /**
     * List of previously diagnosed illnesses.
     */
    private List<String> previousIllnesses;

    /**
     * List of current medications.
     */
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
     * Residential address.
     */
    private String address;
}
