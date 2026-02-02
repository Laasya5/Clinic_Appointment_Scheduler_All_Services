package com.example.appointment_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appointmentId;

    private String patientName;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "doctor_name", referencedColumnName = "doctorName"),
            @JoinColumn(name = "availability_date", referencedColumnName = "availabilityDate")
    })
    private Doctor doctor;
    @Transient
    public LocalDateTime getAppointmentDateTime() {
        return LocalDateTime.of(appointmentDate, appointmentTime);
    }

}