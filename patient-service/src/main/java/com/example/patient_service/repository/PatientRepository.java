package com.example.patient_service.repository;

import com.example.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link Patient} entity.
 *
 * <p>
 * Extends {@link JpaRepository} to provide built-in CRUD operations.
 * Custom query methods are defined using Spring Data JPA method naming conventions.
 * </p>
 *
 * <p>
 * Spring automatically generates the implementation at runtime.
 * </p>
 *
 * @since 1.0
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Retrieves a list of patients by name (case insensitive).
     *
     * Multiple patients can have the same name.
     *
     * @param name patient name
     * @return list of matching patients
     */
    List<Patient> findByNameIgnoreCase(String name);

    /**
     * Retrieves a patient by exact name.
     *
     * @param name patient name
     * @return Optional containing patient if found
     */
    Optional<Patient> findByName(String name);

    /**
     * Checks whether a patient exists by name (case insensitive).
     *
     * @param name patient name
     * @return true if patient exists, otherwise false
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Deletes patients by name (case insensitive).
     *
     * @param name patient name
     */
    void deleteByNameIgnoreCase(String name);
}
