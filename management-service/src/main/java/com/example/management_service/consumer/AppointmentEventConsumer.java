package com.example.management_service.consumer;

import com.example.management_service.document.AppointmentDocument;
import com.example.management_service.document.CancelledCheckupDocument;
import com.example.management_service.document.CompletedCheckupDocument;
import com.example.management_service.event.AppointmentEvent;
import com.example.management_service.repositories.AppointmentMongoRepository;
import com.example.management_service.repositories.CancelledRepo;
import com.example.management_service.repositories.CompletedRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kafka consumer that listens for appointment-related events.
 *
 * <p>
 * This component consumes {@link AppointmentEvent} messages
 * published by the Appointment Service and stores them
 * into appropriate MongoDB collections.
 * </p>
 *
 * <p>
 * Based on event status:
 * </p>
 * <ul>
 *     <li>BOOKED → Stored in active appointments collection</li>
 *     <li>CANCELLED → Removed from active collection and stored in cancelled collection</li>
 *     <li>COMPLETED → Removed from active collection and stored in completed collection</li>
 * </ul>
 *
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AppointmentEventConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(AppointmentEventConsumer.class);

    private final AppointmentMongoRepository appointmentRepo;
    private final CompletedRepo completedRepo;
    private final CancelledRepo cancelledRepo;

    /**
     * Kafka listener method that consumes events from
     * "appointment-events" topic.
     *
     * @param event appointment event received from Kafka
     */
    @KafkaListener(topics = "appointment-events", groupId = "management-service")
    public void consume(AppointmentEvent event) {

        log.info("EVENT RECEIVED -> {}", event);

        switch (event.getStatus()) {

            case "BOOKED":
                handleBooked(event);
                break;

            case "CANCELLED":
                handleCancelled(event);
                break;

            case "COMPLETED":
                handleCompleted(event);
                break;

            default:
                log.warn("Unknown appointment status received: {}", event.getStatus());
                break;
        }
    }

    /**
     * Handles BOOKED event.
     */
    private void handleBooked(AppointmentEvent event) {

        AppointmentDocument booked = new AppointmentDocument();
        booked.setPatientName(event.getPatientName());
        booked.setDoctorName(event.getDoctorName());
        booked.setAppointmentDate(String.valueOf(event.getAppointmentDate()));
        booked.setAppointmentTime(String.valueOf(event.getAppointmentTime()));
        booked.setStatus(event.getStatus());

        try {
            appointmentRepo.save(booked);
            log.info("Booked appointment saved successfully");
        } catch (RuntimeException e) {
            log.error("Error while saving booked appointment", e);
        }
    }

    /**
     * Handles CANCELLED event.
     */
    private void handleCancelled(AppointmentEvent event) {

        appointmentRepo.deleteAll(
                appointmentRepo.findAll().stream()
                        .filter(a ->
                                a.getPatientName().equals(event.getPatientName()) &&
                                        a.getDoctorName().equals(event.getDoctorName()))
                        .toList()
        );

        CancelledCheckupDocument cancelled = new CancelledCheckupDocument();
        cancelled.setPatientName(event.getPatientName());
        cancelled.setDoctorName(event.getDoctorName());
        cancelled.setAppointmentDate(String.valueOf(event.getAppointmentDate()));
        cancelled.setAppointmentTime(String.valueOf(event.getAppointmentTime()));

        cancelledRepo.save(cancelled);
        log.info("Cancelled appointment saved");
    }

    /**
     * Handles COMPLETED event.
     */
    private void handleCompleted(AppointmentEvent event) {

        appointmentRepo.deleteAll(
                appointmentRepo.findAll().stream()
                        .filter(a ->
                                a.getPatientName().equals(event.getPatientName()) &&
                                        a.getDoctorName().equals(event.getDoctorName()))
                        .toList()
        );

        CompletedCheckupDocument completed = new CompletedCheckupDocument();
        completed.setPatientName(event.getPatientName());
        completed.setDoctorName(event.getDoctorName());
        completed.setAppointmentDate(String.valueOf(event.getAppointmentDate()));
        completed.setAppointmentTime(String.valueOf(event.getAppointmentTime()));

        completedRepo.save(completed);
        log.info("Completed appointment saved");
    }
}
