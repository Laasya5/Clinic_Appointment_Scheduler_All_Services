package com.example.appointment_service.exceptions;

/**
 * Custom runtime exception thrown when a requested
 * resource or availability is not found.
 *
 * <p>
 * This exception is typically used in scenarios such as:
 * </p>
 * <ul>
 *     <li>Doctor not available on a specific date</li>
 *     <li>No remaining appointment slots</li>
 *     <li>Patient appointment not found</li>
 * </ul>
 *
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception.
 * </p>
 *
 * @since 1.0
 */
public class NoAvailability extends RuntimeException {

    /**
     * Constructs a new NoAvailability exception
     * with the specified detail message.
     *
     * @param message detailed error description
     */
    public NoAvailability(String message) {
        super(message);
    }
}
