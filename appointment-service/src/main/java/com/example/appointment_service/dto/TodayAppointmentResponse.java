package com.example.appointment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TodayAppointmentResponse {
    private String doctorName;
    private String patientName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;
}