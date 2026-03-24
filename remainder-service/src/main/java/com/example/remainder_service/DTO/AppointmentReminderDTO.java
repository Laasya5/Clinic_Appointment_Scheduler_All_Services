package com.example.remainder_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) used by the Reminder Service
 * to receive upcoming appointment details from the
 * Appointment Service.
 *
 * <p>
 * This DTO contains minimal information required
 * to generate reminders for patients.
 * </p>
 *
 * <p>
 * It is typically fetched via Feign client communication.
 * </p>
 *
 * @since 1.0
 */
@Data
public class AppointmentReminderDTO {

    /**
     * Name of the patient.
     */
    private String patientName;

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
