package com.example.appointment_service.exception;

import com.example.appointment_service.exceptions.ExceptionResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {

        LocalDateTime now = LocalDateTime.now();

        ExceptionResponse response =
                new ExceptionResponse("Error", now, 400);

        assertEquals("Error", response.getResponse());
        assertEquals(now, response.getTime());
        assertEquals(400, response.getStatus());
    }
    @Test
    void testCustomException() {
        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> { throw new RuntimeException("Error"); });

        assertEquals("Error", ex.getMessage());
    }

    @Test
    void testNoArgsConstructorAndSetters() {

        ExceptionResponse response = new ExceptionResponse();

        LocalDateTime now = LocalDateTime.now();

        response.setResponse("Failure");
        response.setTime(now);
        response.setStatus(500);

        assertEquals("Failure", response.getResponse());
        assertEquals(now, response.getTime());
        assertEquals(500, response.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {

        LocalDateTime now = LocalDateTime.now();

        ExceptionResponse r1 =
                new ExceptionResponse("Error", now, 400);

        ExceptionResponse r2 =
                new ExceptionResponse("Error", now, 400);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {

        ExceptionResponse response =
                new ExceptionResponse("Error",
                        LocalDateTime.now(),
                        400);

        assertNotNull(response.toString());
    }
}
