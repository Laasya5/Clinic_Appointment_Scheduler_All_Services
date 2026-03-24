package com.example.management_service.config;

import com.example.management_service.event.AppointmentEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Consumer configuration for the Management Service.
 *
 * <p>
 * Configures Kafka consumer properties and sets up
 * listener container factory for consuming
 * {@link AppointmentEvent} messages.
 * </p>
 *
 * <p>
 * This service listens to appointment-related events
 * published by the Appointment Service.
 * </p>
 *
 * @since 1.0
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    /**
     * Creates the Kafka ConsumerFactory.
     *
     * <p>
     * Configures:
     * </p>
     * <ul>
     *     <li>Bootstrap servers</li>
     *     <li>Consumer group ID</li>
     *     <li>Key deserializer</li>
     *     <li>Value deserializer (JSON → AppointmentEvent)</li>
     * </ul>
     *
     * @return ConsumerFactory for AppointmentEvent
     */
    @Bean
    public ConsumerFactory<String, AppointmentEvent> consumerFactory() {

        JacksonJsonDeserializer<AppointmentEvent> deserializer =
                new JacksonJsonDeserializer<>(AppointmentEvent.class);

        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "management-service");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    /**
     * Creates Kafka listener container factory.
     *
     * <p>
     * Used by {@link org.springframework.kafka.annotation.KafkaListener}
     * to listen to Kafka topics.
     * </p>
     *
     * @return ConcurrentKafkaListenerContainerFactory instance
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AppointmentEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AppointmentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
