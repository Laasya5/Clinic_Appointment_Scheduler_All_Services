package com.example.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Response DTO representing appointment details
 * returned to the client.
 *
 * <p>
 * This DTO is used to provide structured information
 * about a booked, cancelled, or completed appointment.
 * </p>
 *
 * <p>
 * It ensures that only relevant appointment data
 * is exposed through the API.
 * </p>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class TodayAppointmentResponse {
    /**
     * Appointment Id.
     */
    private Long appointmentId;

    /**
     * Name of the doctor assigned to the appointment.
     */
    private String doctorName;

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
}
