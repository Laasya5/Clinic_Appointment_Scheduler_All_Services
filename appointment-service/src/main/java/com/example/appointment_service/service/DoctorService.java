package com.example.appointment_service.service;

import com.example.appointment_service.entity.*;
import com.example.appointment_service.repository.DoctorRepository;
import com.example.appointment_service.exceptions.NoAvailability;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor createDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    public Doctor getDoctor(String name, LocalDate date) {
        return repository.findById(new DoctorId(name, date))
                .orElseThrow(() -> new NoAvailability("Doctor not found"));
    }
}
