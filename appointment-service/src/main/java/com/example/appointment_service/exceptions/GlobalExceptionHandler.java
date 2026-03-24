package com.example.appointment_service.exceptions;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Global exception handler for the Appointment Service.
 *
 * <p>
 * This class intercepts exceptions thrown from controllers
 * and returns a structured {@link ExceptionResponse}
 * to the client.
 * </p>
 *
 * <p>
 * Ensures consistent error handling across the service.
 * </p>
 *
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all RuntimeException types.
     *
     * @param e the exception thrown
     * @return ResponseEntity containing structured error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handle(RuntimeException e) {

        ExceptionResponse response = new ExceptionResponse(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
