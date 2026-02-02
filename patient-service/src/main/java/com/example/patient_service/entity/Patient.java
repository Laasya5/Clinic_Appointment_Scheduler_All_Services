package com.example.patient_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private String bloodGroup;

    @ElementCollection
    private List<String> chronicConditions;

    @ElementCollection
    private List<String> previousIllnesses;

    @ElementCollection
    private List<String> currentMedications;

    private String phoneNumber;
    private String email;
    private String address;
}
