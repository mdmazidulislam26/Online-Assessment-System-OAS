package com.mazidul.quiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Quiz application.
 * This application is built using Spring Boot.
 */
@SpringBootApplication
@EnableFeignClients // Enables the use of Feign clients for declarative REST API calls.
public class QuizApplication {

	/**
	 * Main method that serves as the entry point of the Spring Boot application.
	 *
	 * @param args command-line arguments passed during the application startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);
	}

}
