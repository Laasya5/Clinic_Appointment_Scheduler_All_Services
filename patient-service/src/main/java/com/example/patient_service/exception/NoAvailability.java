package com.example.patient_service.exception;

/**
 * Custom runtime exception thrown when a requested resource
 * (such as a Patient record) is not available.
 *
 * <p>
 * This exception is typically used in the service layer
 * when a database query does not return expected results.
 * </p>
 *
 * <p>
 * It extends {@link RuntimeException}, so it is unchecked
 * and does not require explicit handling.
 * </p>
 *
 * @since 1.0
 */
public class NoAvailability extends RuntimeException {

    /**
     * Constructs a new NoAvailability exception with the specified detail message.
     *
     * @param message detailed error message explaining the cause
     */
    public NoAvailability(String message) {
        super(message);
    }
}
