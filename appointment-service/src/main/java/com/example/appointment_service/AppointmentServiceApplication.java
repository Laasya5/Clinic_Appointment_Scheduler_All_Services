package com.example.appointment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Appointment Service application.
 *
 * <p>
 * This class bootstraps the Spring Boot application and enables:
 * </p>
 *
 * <ul>
 *     <li>Auto-configuration</li>
 *     <li>Component scanning</li>
 *     <li>Scheduling support</li>
 * </ul>
 *
 * <p>
 * The service manages appointment booking, cancellation,
 * auto-completion, and Kafka-based event publishing.
 * </p>
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableScheduling
public class AppointmentServiceApplication {

	/**
	 * Main method used to launch the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}
}
