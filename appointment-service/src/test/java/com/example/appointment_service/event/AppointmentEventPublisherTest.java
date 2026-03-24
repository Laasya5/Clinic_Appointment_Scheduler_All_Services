package com.example.appointment_service.event;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class AppointmentEventPublisherTest {

    @Test
    void testPublish() {
        KafkaTemplate<String, AppointmentEvent> kafkaTemplate =
                mock(KafkaTemplate.class);

        AppointmentEventPublisher publisher =
                new AppointmentEventPublisher(kafkaTemplate);

        AppointmentEvent event =
                new AppointmentEvent("BOOKED","John","Dr A",null,null);

        publisher.publish(event);

        verify(kafkaTemplate, times(1))
                .send("appointment-events", event);
    }
}
