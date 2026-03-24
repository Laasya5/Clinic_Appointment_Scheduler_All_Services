package com.example.management_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Event model representing appointment-related messages
 * consumed from Kafka.
 *
 * <p>
 * This class mirrors the event structure published by
 * the Appointment Service.
 * </p>
 *
 * <p>
 * It is used by the Management Service to process
 * BOOKED, CANCELLED, and COMPLETED appointment events.
 * </p>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentEvent {

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

    /**
     * Current status of the appointment.
     * Expected values: BOOKED, CANCELLED, COMPLETED
     */
    private String status;
}
