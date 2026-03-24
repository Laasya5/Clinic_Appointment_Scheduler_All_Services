package com.example.appointment_service.event;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Event class representing an appointment-related action
 * that is published to Kafka.
 *
 * <p>
 * This event is used for inter-service communication
 * between microservices (e.g., Appointment Service → Management Service).
 * </p>
 *
 * <p>
 * It contains appointment details and the current status
 * such as BOOKED, CANCELLED, or COMPLETED.
 * </p>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEvent {

    /**
     * Current status of the appointment.
     * Example values: BOOKED, CANCELLED, COMPLETED.
     */
    private String status;

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
