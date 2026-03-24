package com.example.appointment_service.service;

import com.example.appointment_service.entity.*;
import com.example.appointment_service.repository.DoctorRepository;
import com.example.appointment_service.exceptions.NoAvailability;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service layer responsible for managing Doctor availability.
 *
 * <p>
 * Handles creation and retrieval of doctor availability records.
 * Each doctor is uniquely identified by a composite key
 * consisting of doctor name and availability date.
 * </p>
 *
 * <p>
 * Interacts with {@link DoctorRepository} to perform database operations.
 * </p>
 *
 * @since 1.0
 */
@Service
public class DoctorService {

    private final DoctorRepository repository;

    /**
     * Constructor-based dependency injection.
     *
     * @param repository doctor repository
     */
    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new doctor availability entry.
     *
     * <p>
     * Stores total and remaining appointment slots
     * for a doctor on a specific date.
     * </p>
     *
     * @param doctor Doctor entity containing availability details
     * @return saved Doctor entity
     */
    public Doctor createDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    /**
     * Retrieves doctor availability by name and date.
     *
     * <p>
     * Uses composite primary key (DoctorId)
     * to uniquely identify the record.
     * </p>
     *
     * @param name doctor's name
     * @param date availability date
     * @return Doctor entity
     * @throws NoAvailability if doctor not found
     */
    public Doctor getDoctor(String name, LocalDate date) {
        return repository.findById(new DoctorId(name, date))
                .orElseThrow(() -> new NoAvailability("Doctor not found"));
    }

    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }
}
