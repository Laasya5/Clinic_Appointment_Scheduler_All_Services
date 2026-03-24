package com.example.appointment_service.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret",
                "12345678901234567890123456789012");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 100000);
    }

    @Test
    void testGenerateAndValidate() {

        String token =
                jwtUtil.generateToken("user","DOCTOR");

        assertTrue(jwtUtil.validateToken(token));
        assertEquals("user",
                jwtUtil.extractUsername(token));
        assertEquals("DOCTOR",
                jwtUtil.extractRole(token));
    }

    @Test
    void testInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid.token.here"));
    }
}