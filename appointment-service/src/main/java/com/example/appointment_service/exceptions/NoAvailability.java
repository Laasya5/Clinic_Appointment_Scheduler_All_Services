package com.example.appointment_service.exceptions;

public class NoAvailability extends RuntimeException {
    public NoAvailability(String message) {
        super(message);
    }
}
