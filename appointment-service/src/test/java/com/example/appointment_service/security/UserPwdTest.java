package com.example.appointment_service.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserPwdTest {

    @Test
    void testGetterSetter() {

        UserPwd userPwd = new UserPwd();

        userPwd.setUsername("john");
        userPwd.setPassword("pass");
        userPwd.setRole("ADMIN");

        assertEquals("john", userPwd.getUsername());
        assertEquals("pass", userPwd.getPassword());
        assertEquals("ADMIN", userPwd.getRole());
    }
}