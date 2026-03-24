package com.example.remainder_service.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) used by the Reminder Service
 * to receive patient contact details from the Patient Service.
 *
 * <p>
 * This DTO contains only the necessary contact information
 * required to send reminders such as email or phone notifications.
 * </p>
 *
 * <p>
 * It is typically retrieved via Feign client communication.
 * </p>
 *
 * @since 1.0
 */
@Data
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
     * Patient's phone number.
     */
    private String phone;
}
