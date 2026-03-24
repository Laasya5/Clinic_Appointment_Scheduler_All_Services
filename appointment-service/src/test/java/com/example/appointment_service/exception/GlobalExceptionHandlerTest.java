package com.example.appointment_service.exception;

import com.example.appointment_service.exceptions.ExceptionResponse;
import com.example.appointment_service.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void testHandleRuntimeException() {

        RuntimeException ex =
                new RuntimeException("Something failed");

        ResponseEntity<ExceptionResponse> response =
                handler.handle(ex);

        assertEquals(HttpStatus.BAD_REQUEST,
                response.getStatusCode());

        ExceptionResponse body = response.getBody();

        assertNotNull(body);
        assertEquals("Something failed",
                body.getResponse());
        assertEquals(HttpStatus.BAD_REQUEST.value(),
                body.getStatus());
        assertNotNull(body.getTime());
    }
}
