package com.example.appointment_service.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object (DTO) used to capture
 * appointment booking details from client requests.
 *
 * <p>
 * This DTO is typically used in appointment creation APIs.
 * It contains the necessary information required to book
 * an appointment with a doctor.
 * </p>
 *
 * <p>
 * It separates external API data from internal
 * entity models.
 * </p>
 *
 * @since 1.0
 */
@Data
public class AppointmentDetails {

    /**
     * Name of the patient booking the appointment.
     */
    private String patientName;

    /**
     * Name of the doctor for the appointment.
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
