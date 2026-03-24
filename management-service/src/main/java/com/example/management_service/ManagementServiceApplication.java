package com.example.management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Main entry point for the Management Service application.
 *
 * <p>
 * The Management Service:
 * </p>
 * <ul>
 *     <li>Consumes appointment-related events from Kafka</li>
 *     <li>Maintains MongoDB read models</li>
 *     <li>Separates active, cancelled, and completed appointments</li>
 * </ul>
 *
 * <p>
 * Uses event-driven architecture to stay synchronized
 * with the Appointment Service.
 * </p>
 *
 * @since 1.0
 */
@EnableKafka
@SpringBootApplication
public class ManagementServiceApplication {

	/**
	 * Main method used to start the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}
}
