package com.multitenant.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Multi-Tenant Spring Boot application.
 *
 * Responsible for initializing the application context
 * and bootstrapping multi-tenant system components.
 */
@SpringBootApplication
public class MultitenantSpringbootBackendApplication {

	/**
	 * Starts the multi-tenant application.
	 *
	 * Loads application configuration,
	 * registers beans, and launches
	 * the embedded web server.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MultitenantSpringbootBackendApplication.class, args);
	}

}