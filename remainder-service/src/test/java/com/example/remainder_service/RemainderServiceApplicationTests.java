package com.example.remainder_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class RemainderServiceApplicationTest {

	@Test
	void mainMethodShouldRun() {
		assertDoesNotThrow(() ->
				RemainderServiceApplication.main(new String[] {})
		);
	}

	@Test
	void contextLoads() {
		// This ensures Spring context loads properly
	}
}
