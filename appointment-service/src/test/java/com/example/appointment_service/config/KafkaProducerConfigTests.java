package com.example.appointment_service.config;

import com.example.appointment_service.event.AppointmentEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigTests {

    private final KafkaProducerConfig config = new KafkaProducerConfig();

    @Test
    void testProducerFactoryNotNull() {
        ProducerFactory<String, AppointmentEvent> factory =
                config.producerFactory();
        assertNotNull(factory);
    }

    @Test
    void testKafkaTemplateNotNull() {
        KafkaTemplate<String, AppointmentEvent> template =
                config.kafkaTemplate();
        assertNotNull(template);
    }
}
