package com.example.appointment_service.security;

import com.example.appointment_service.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBranches() {

        User u1 = new User();
        User u2 = new User();

        assertEquals(u1,u1);
        assertNotEquals(u1,null);
        assertNotEquals(u1,"abc");

        u1.setUsername("john");
        u2.setUsername("jane");

        assertNotEquals(u1,u2);
        assertNotNull(u1.toString());
    }

    @Test
    void testUserPwd() {
        UserPwd pwd = new UserPwd();
        pwd.setUsername("john");
        pwd.setPassword("pass");
        pwd.setRole("DOCTOR");

        assertEquals("john",pwd.getUsername());
    }
}