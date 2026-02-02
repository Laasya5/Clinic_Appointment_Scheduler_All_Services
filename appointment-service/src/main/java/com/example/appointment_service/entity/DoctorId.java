package com.example.appointment_service.entity;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorId implements Serializable {
    private String doctorName;
    private LocalDate availabilityDate;
}
