package com.example.patient_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling application-wide exceptions.
 *
 * <p>
 * This class intercepts exceptions thrown from controllers
 * and returns a structured error response to the client.
 * </p>
 *
 * <p>
 * It ensures consistent error handling across the application.
 * </p>
 *
 * @since 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all RuntimeException types thrown within the application.
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
