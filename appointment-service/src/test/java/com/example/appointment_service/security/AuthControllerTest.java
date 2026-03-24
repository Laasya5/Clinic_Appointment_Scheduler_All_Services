package com.example.appointment_service.security;

import com.example.appointment_service.entity.User;
import com.example.appointment_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Test
    void testLogin() {

        AuthenticationManager manager = mock(AuthenticationManager.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        UserRepository repo = mock(UserRepository.class);
        SecurityService service = mock(SecurityService.class);

        AuthController controller =
                new AuthController(manager,jwtUtil,repo,service);

        User user = new User();
        user.setUsername("john");
        user.setRole("DOCTOR");

        when(repo.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(jwtUtil.generateToken("john","DOCTOR"))
                .thenReturn("token");

        String token =
                controller.login("john","pass");

        assertEquals("token", token);
    }

    @Test
    void testCreateUser() {

        AuthController controller =
                new AuthController(
                        mock(AuthenticationManager.class),
                        mock(JwtUtil.class),
                        mock(UserRepository.class),
                        mock(SecurityService.class)
                );

        UserPwd pwd = new UserPwd();
        pwd.setUsername("john");

        controller.create(pwd);
    }
}