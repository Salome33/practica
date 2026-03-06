package com.miempresa.reactiveapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveApiApplication {
	public static void main(String[] args) {
		// Diagnóstico temporal
		System.out.println("=== DATABASE_URL: " + System.getenv("DATABASE_URL"));
		System.out.println("=== DATABASE_USER: " + System.getenv("DATABASE_USER"));
		System.out.println("=== PORT: " + System.getenv("PORT"));

		SpringApplication.run(ReactiveApiApplication.class, args);
	}
}