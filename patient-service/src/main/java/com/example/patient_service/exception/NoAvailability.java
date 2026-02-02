package com.example.patient_service.exception;

public class NoAvailability extends RuntimeException {
    public NoAvailability(String message) {
        super(message);
    }
}
