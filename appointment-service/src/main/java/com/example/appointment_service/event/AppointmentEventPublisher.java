package com.example.appointment_service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Publisher service responsible for sending appointment events to Kafka.
 *
 * <p>
 * This class publishes {@link AppointmentEvent} messages
 * to the configured Kafka topic for inter-service communication.
 * </p>
 *
 * <p>
 * Used to notify other microservices (e.g., Management Service)
 * about appointment status changes such as BOOKED,
 * CANCELLED, or COMPLETED.
 * </p>
 *
 * @since 1.0
 */
@Service
public class AppointmentEventPublisher {

    /**
     * Logger instance for logging event publishing activity.
     */
    private static final Logger log =
            LoggerFactory.getLogger(AppointmentEventPublisher.class);

    /**
     * Kafka topic name for appointment events.
     */
    private static final String TOPIC = "appointment-events";

    /**
     * KafkaTemplate used to send messages to Kafka.
     */
    private final KafkaTemplate<String, AppointmentEvent> kafkaTemplate;

    /**
     * Constructor-based dependency injection.
     *
     * @param kafkaTemplate KafkaTemplate for publishing events
     */
    public AppointmentEventPublisher(
            KafkaTemplate<String, AppointmentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publishes an appointment event to Kafka.
     *
     * @param event appointment event containing status and details
     */
    public void publish(AppointmentEvent event) {
        kafkaTemplate.send(TOPIC, event);
        log.info("EVENT SENT -> {}", event);
    }
}
