package com.example.appointment_service.exceptions;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Represents a structured error response returned to clients
 * when an exception occurs within the Appointment Service.
 *
 * <p>
 * This class is used by the global exception handler to provide
 * consistent error details including:
 * </p>
 *
 * <ul>
 *     <li>Error message</li>
 *     <li>Timestamp of the error</li>
 *     <li>HTTP status code</li>
 * </ul>
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    /**
     * Error message describing the issue.
     */
    private String response;

    /**
     * Timestamp when the error occurred.
     */
    private LocalDateTime time;

    /**
     * HTTP status code associated with the error.
     */
    private int status;
}
