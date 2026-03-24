package com.example.management_service.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * MongoDB document representing a completed appointment.
 *
 * <p>
 * This document is stored in the <b>completed_checkups</b> collection.
 * It is created when a COMPLETED event is consumed
 * from Kafka by the Management Service.
 * </p>
 *
 * <p>
 * Used for maintaining historical records of successfully
 * completed appointments.
 * </p>
 *
 * @since 1.0
 */
@Data
@Document(collection = "completed_checkups")
public class CompletedCheckupDocument {

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
     * Date of the completed appointment.
     * (Currently stored as String.)
     */
    private String appointmentDate;

    /**
     * Time of the completed appointment.
     * (Currently stored as String.)
     */
    private String appointmentTime;
}
