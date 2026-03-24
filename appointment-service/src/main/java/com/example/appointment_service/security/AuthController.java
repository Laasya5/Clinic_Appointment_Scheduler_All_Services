package com.example.appointment_service.security;

import com.example.appointment_service.dto.AppointmentDetails;
import com.example.appointment_service.dto.TodayAppointmentResponse;
import com.example.appointment_service.entity.User;
import com.example.appointment_service.repository.UserRepository;
import org.springframework.security.authentication.
        AuthenticationManager;
import org.springframework.security.authentication.
        UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository, SecurityService securityService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }

    @PostMapping("/create")
    public User create(@RequestBody UserPwd userpwd){
        return securityService.createUser(userpwd);

    }
}
