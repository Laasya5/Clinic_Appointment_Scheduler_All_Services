package com.example.appointment_service.repository;

import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.entity.DoctorId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Doctor} entities.
 *
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations
 * for the Doctor entity.
 * </p>
 *
 * <p>
 * The entity uses a composite primary key defined by {@link DoctorId},
 * which consists of:
 * </p>
 *
 * <ul>
 *     <li>doctorName</li>
 *     <li>availabilityDate</li>
 * </ul>
 *
 * <p>
 * Spring Data JPA automatically generates the implementation
 * at runtime.
 * </p>
 *
 * @since 1.0
 */
public interface DoctorRepository extends JpaRepository<Doctor, DoctorId> {
}
