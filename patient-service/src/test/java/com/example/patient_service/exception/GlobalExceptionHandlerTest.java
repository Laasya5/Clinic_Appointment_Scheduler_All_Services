package com.example.patient_service.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void testHandleRuntimeException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        RuntimeException ex = new RuntimeException("Error occurred");

        ResponseEntity<ExceptionResponse> response =
                handler.handle(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Error occurred",
                response.getBody().getResponse());
    }
}
