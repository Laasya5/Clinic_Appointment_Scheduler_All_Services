package com.example.appointment_service.security;

import com.example.appointment_service.repository.UserRepository;
import com.example.appointment_service.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private SecurityService service;

    @Test
    void testCreateUser() {

        UserPwd userPwd = new UserPwd();
        userPwd.setUsername("john");
        userPwd.setPassword("pass");
        userPwd.setRole("DOCTOR");

        User savedUser = new User();
        savedUser.setUsername("john");
        savedUser.setPassword("pass");
        savedUser.setRole("DOCTOR");

        when(repository.save(any(User.class))).thenReturn(savedUser);

        User result = service.createUser(userPwd);

        assertEquals("john", result.getUsername());
        assertEquals("pass", result.getPassword());
        assertEquals("DOCTOR", result.getRole());

        verify(repository, times(1)).save(any(User.class));
    }
}