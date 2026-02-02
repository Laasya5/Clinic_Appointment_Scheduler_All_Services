package com.example.patient_service.repository;

import com.example.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Allows multiple patients with same name
    List<Patient> findByNameIgnoreCase(String name);

    void deleteByNameIgnoreCase(String name);
}
