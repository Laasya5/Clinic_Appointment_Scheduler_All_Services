package com.example.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main entry point for the API Gateway application.
 *
 * <p>
 * The API Gateway acts as a single entry point
 * for all client requests in the microservices architecture.
 * </p>
 *
 * <p>
 * Responsibilities:
 * </p>
 * <ul>
 *     <li>Route requests to appropriate microservices</li>
 *     <li>Integrate with service discovery</li>
 *     <li>Provide centralized configuration</li>
 *     <li>Enable security, logging, and monitoring</li>
 * </ul>
 *
 * <p>
 * This application registers itself with the service registry
 * using {@link EnableDiscoveryClient}.
 * </p>
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	/**
	 * Main method used to launch the API Gateway.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
