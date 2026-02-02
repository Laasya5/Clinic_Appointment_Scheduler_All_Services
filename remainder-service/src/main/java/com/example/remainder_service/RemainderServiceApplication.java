package com.example.remainder_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class RemainderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemainderServiceApplication.class, args);
	}

}
