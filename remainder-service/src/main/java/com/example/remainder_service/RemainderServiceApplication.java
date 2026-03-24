package com.example.remainder_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the Reminder Service application.
 *
 * <p>
 * This application:
 * </p>
 * <ul>
 *     <li>Enables Spring Boot auto-configuration</li>
 *     <li>Enables Feign clients for inter-service communication</li>
 *     <li>Enables scheduling for periodic reminder execution</li>
 * </ul>
 *
 * <p>
 * The Reminder Service fetches upcoming appointments,
 * retrieves patient contact details, and sends reminders.
 * </p>
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class RemainderServiceApplication {

	/**
	 * Main method used to launch the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RemainderServiceApplication.class, args);
	}
}
