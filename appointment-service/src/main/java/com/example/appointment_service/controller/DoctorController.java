package com.example.appointment_service.controller;

import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Doctor create(@RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }

    @GetMapping
    public Doctor get(
            @RequestParam String doctorName,
            @RequestParam LocalDate availabilityDate) {
        return service.getDoctor(doctorName, availabilityDate);
    }}