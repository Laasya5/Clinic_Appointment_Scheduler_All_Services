package com.example.patient_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Patient Service application.
 *
 * <p>
 * This class bootstraps the Spring Boot application
 * and enables auto-configuration, component scanning,
 * and configuration properties support.
 * </p>
 *
 * <p>
 * The application manages patient-related operations
 * including CRUD functionalities exposed via REST APIs.
 * </p>
 *
 * @since 1.0
 */
@SpringBootApplication
public class PatientServiceApplication {

	/**
	 * Main method used to launch the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}
}
