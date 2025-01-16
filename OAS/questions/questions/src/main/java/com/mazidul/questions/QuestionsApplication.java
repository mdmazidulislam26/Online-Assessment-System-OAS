package com.mazidul.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main entry point for the Spring Boot application
@SpringBootApplication // Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan annotations.
public class QuestionsApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during application startup.
	 */
	public static void main(String[] args) {
		// Starts the Spring Boot application by initializing the application context.
		SpringApplication.run(QuestionsApplication.class, args);
	}
}
