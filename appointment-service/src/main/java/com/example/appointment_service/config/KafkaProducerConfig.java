package com.example.appointment_service.config;

import com.example.appointment_service.event.AppointmentEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Producer configuration class.
 *
 * <p>
 * Configures Kafka producer properties such as
 * bootstrap servers and serializers.
 * </p>
 *
 * <p>
 * This configuration enables publishing {@link AppointmentEvent}
 * messages to Kafka topics.
 * </p>
 *
 * @since 1.0
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Creates and configures the ProducerFactory.
     *
     * <p>
     * Defines Kafka producer properties including:
     * bootstrap server, key serializer, and value serializer.
     * </p>
     *
     * @return ProducerFactory for AppointmentEvent
     */
    @Bean
    public ProducerFactory<String, AppointmentEvent> producerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Disable type headers to simplify message structure
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Creates KafkaTemplate used to send messages to Kafka.
     *
     * @return KafkaTemplate instance
     */
    @Bean
    public KafkaTemplate<String, AppointmentEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
