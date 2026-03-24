package com.example.patient_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a structured error response returned to the client
 * when an exception occurs.
 *
 * <p>
 * This class is used by {@link GlobalExceptionHandler}
 * to provide consistent error details including:
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
     * Error message describing what went wrong.
     */
    private String response;

    /**
     * Timestamp when the error occurred.
     */
    private LocalDateTime time;

    /**
     * HTTP status code of the error.
     */
    private int status;
}
