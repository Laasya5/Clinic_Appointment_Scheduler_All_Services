package com.example.appointment_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "doctors")
@IdClass(DoctorId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    private String doctorName;

    @Id
    private LocalDate availabilityDate;

    private int totalAppointments;
    private int remainingAppointments;
}
