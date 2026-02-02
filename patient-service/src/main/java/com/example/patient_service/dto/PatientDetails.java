package com.example.patient_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class PatientDetails {

    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private List<String> chronicConditions;
    private List<String> previousIllnesses;
    private List<String> currentMedications;
    private String phoneNumber;
    private String email;
    private String address;
}
