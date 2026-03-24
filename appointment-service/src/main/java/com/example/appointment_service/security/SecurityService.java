package com.example.appointment_service.security;

import com.example.appointment_service.entity.User;
import com.example.appointment_service.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public SecurityService(UserRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(UserPwd userpwd){

        User user = new User();
        user.setUsername(userpwd.getUsername());
        user.setPassword(passwordEncoder.encode(userpwd.getPassword()));
        user.setRole(userpwd.getRole());

        return repository.save(user);
    }
}