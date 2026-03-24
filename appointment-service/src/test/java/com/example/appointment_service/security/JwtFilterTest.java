package com.example.appointment_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

class JwtFilterTest {

    private JwtUtil jwtUtil;
    private JwtFilter jwtFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setup() {
        jwtUtil = mock(JwtUtil.class);
        jwtFilter = new JwtFilter(jwtUtil);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testNoAuthorizationHeader() throws Exception {

        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testHeaderWithoutBearer() throws Exception {

        when(request.getHeader("Authorization")).thenReturn("InvalidHeader");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testInvalidToken() throws Exception {

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.validateToken("token")).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testValidToken() throws Exception {

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.validateToken("token")).thenReturn(true);
        when(jwtUtil.extractUsername("token")).thenReturn("john");
        when(jwtUtil.extractRole("token")).thenReturn("DOCTOR");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}