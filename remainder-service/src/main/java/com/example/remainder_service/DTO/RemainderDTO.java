package com.example.remainder_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) representing a complete reminder payload.
 *
 * <p>
 * This DTO combines patient contact details and appointment
 * information required to send a reminder notification.
 * </p>
 *
 * <p>
 * It is typically constructed inside the Reminder Service
 * after fetching data from:
 * </p>
 * <ul>
 *     <li>Appointment Service</li>
 *     <li>Patient Service</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
public class RemainderDto {

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

    /**
     * Name of the doctor.
     */
    private String doctorName;

    /**
     * Date of the appointment.
     */
    private LocalDate appointmentDate;

    /**
     * Time of the appointment.
     */
    private LocalTime appointmentTime;
}
