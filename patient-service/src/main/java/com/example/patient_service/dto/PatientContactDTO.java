package com.example.patient_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientContactDTO {
    private String name;
    private String email;
    private String phoneNumber;
}
