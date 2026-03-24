package com.example.appointment_service.security;


import lombok.Data;

@Data
public class UserPwd {

    private String username;
    private String password;
    private String role;

}
