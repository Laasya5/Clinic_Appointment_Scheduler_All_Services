package com.example.appointment_service.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Test
    void testPasswordEncoder() {

        SecurityConfig config =
                new SecurityConfig(mock(JwtFilter.class));

        PasswordEncoder encoder =
                config.passwordEncoder();

        assertTrue(encoder.matches("test",
                encoder.encode("test")));
    }

    @Test
    void testAuthenticationManager() throws Exception {

        AuthenticationConfiguration config =
                mock(AuthenticationConfiguration.class);

        when(config.getAuthenticationManager())
                .thenReturn(mock(
                        org.springframework.security.authentication.AuthenticationManager.class));

        SecurityConfig securityConfig =
                new SecurityConfig(mock(JwtFilter.class));

        assertNotNull(securityConfig.authenticationManager(config));
    }
}