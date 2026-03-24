package com.example.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) used to expose
 * minimal patient contact information.
 *
 * <p>
 * This DTO is typically used when only basic contact
 * details are required, such as sending notifications
 * or reminders.
 * </p>
 *
 * <p>
 * It prevents exposing full patient medical details
 * and ensures controlled data sharing.
 * </p>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class PatientContactDTO {

    /**
     * Patient's full name.
     */
    private String name;

    /**
     * Patient's email address.
     */
    private String email;

    /**
     * Patient's contact phone number.
     */
    private String phoneNumber;
}
