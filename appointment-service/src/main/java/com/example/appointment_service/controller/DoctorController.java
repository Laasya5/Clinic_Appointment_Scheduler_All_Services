package com.example.appointment_service.controller;

import com.example.appointment_service.entity.Appointment;
import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.repository.AppointmentRepository;
import com.example.appointment_service.service.DoctorService;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST Controller for managing Doctor availability.
 *
 * <p>
 * Provides endpoints to create doctor availability
 * and retrieve doctor details based on name and date.
 * </p>
 *
 * <p>
 * Base URL: <b>/api/appointment-service/doctors</b>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/appointment-service/doctors")
public class DoctorController {

    private final DoctorService service;
    private final AppointmentRepository repository;

    /**
     * Constructor-based dependency injection.
     *
     * @param service doctor service layer
     */
    public DoctorController(DoctorService service,AppointmentRepository repository) {
        this.service = service;
        this.repository=repository;
    }

    /**
     * Creates a doctor availability entry.
     *
     * @param doctor doctor entity containing availability details
     * @return saved Doctor entity
     */
    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/create")
    public Doctor create(@RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }

    /**
     * Retrieves doctor availability by doctor name and date.
     *
     * @param doctorName name of the doctor
     * @param availabilityDate availability date
     * @return Doctor entity for the given name and date
     */
    @GetMapping
    public Doctor get(
            @RequestParam String doctorName,
            @RequestParam LocalDate availabilityDate) {
        return service.getDoctor(doctorName, availabilityDate);
    }

    @BatchMapping
    public Map<Doctor, List<Appointment>> appointments(List<Doctor> doctors) {

        List<Appointment> appointments =
                repository.findByDoctorIn(doctors);

        return appointments.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctor));
    }

    @QueryMapping
    public List<Doctor> doctors() {
        return service.getAllDoctors();
    }
}
