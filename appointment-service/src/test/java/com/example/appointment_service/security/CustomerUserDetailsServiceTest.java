package com.example.appointment_service.security;

import com.example.appointment_service.entity.User;
import com.example.appointment_service.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerUserDetailsServiceTest {

    @Test
    void testLoadUserSuccess() {

        UserRepository repo = mock(UserRepository.class);
        CustomUserDetailsService service =
                new CustomUserDetailsService(repo);

        User user = new User();
        user.setUsername("john");
        user.setPassword("pass");

        when(repo.findByUsername("john"))
                .thenReturn(Optional.of(user));

        assertNotNull(service.loadUserByUsername("john"));
    }

    @Test
    void testLoadUserNotFound() {

        UserRepository repo = mock(UserRepository.class);
        CustomUserDetailsService service =
                new CustomUserDetailsService(repo);

        when(repo.findByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("john"));
    }
}