package com.example.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Main entry point for the Eureka Server.
 *
 * <p>
 * Eureka Server acts as a Service Registry in the microservices architecture.
 * All microservices register themselves with Eureka, and clients can discover
 * service instances dynamically.
 * </p>
 *
 * <p>
 * Responsibilities:
 * </p>
 * <ul>
 *     <li>Service registration</li>
 *     <li>Service discovery</li>
 *     <li>Health monitoring of services</li>
 *     <li>Dynamic load balancing support</li>
 * </ul>
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

	/**
	 * Main method to launch the Eureka Server.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}
}
