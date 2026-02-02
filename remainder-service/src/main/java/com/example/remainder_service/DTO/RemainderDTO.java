package com.example.remainder_service.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class RemainderDTO {
    public String Name;
    public String Email;
    public String Phone;
    public String DoctorName;
    public LocalDate AppointmentDate;
    public LocalTime AppointmentTime;
}
