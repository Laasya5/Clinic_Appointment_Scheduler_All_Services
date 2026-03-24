package com.example.management_service.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB document representing an active (booked) appointment.
 *
 * <p>
 * This document is stored in the <b>appointments</b> collection
 * within MongoDB.
 * </p>
 *
 * <p>
 * It is populated when a BOOKED event is received
 * from Kafka by the Management Service.
 * </p>
 *
 * @since 1.0
 */
@Data
@Document(collection = "appointments")
public class AppointmentDocument {

    /**
     * Unique identifier for the MongoDB document.
     */
    @Id
    private String id;

    /**
     * Name of the patient.
     */
    private String patientName;

    /**
     * Name of the doctor.
     */
    private String doctorName;

    /**
     * Date of the appointment (stored as String).
     */
    private String appointmentDate;

    /**
     * Time of the appointment (stored as String).
     */
    private String appointmentTime;

    /**
     * Current status of the appointment.
     */
    private String status;
}
