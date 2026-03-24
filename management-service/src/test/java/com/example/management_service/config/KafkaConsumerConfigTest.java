package com.example.management_service.config;

import com.example.management_service.event.AppointmentEvent;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConsumerConfigTest {

    private final KafkaConsumerConfig config = new KafkaConsumerConfig();

    @Test
    void testConsumerFactory() {
        ConsumerFactory<String, AppointmentEvent> factory =
                config.consumerFactory();

        assertNotNull(factory);
    }

    @Test
    void testKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AppointmentEvent> factory =
                config.kafkaListenerContainerFactory();

        assertNotNull(factory);
        assertNotNull(factory.getConsumerFactory());
    }
}
