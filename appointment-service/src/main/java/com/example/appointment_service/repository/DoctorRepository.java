package com.example.appointment_service.repository;

import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.entity.DoctorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, DoctorId> {
}
