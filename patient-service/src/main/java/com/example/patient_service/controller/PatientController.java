package com.example.patient_service.controller;

import com.example.patient_service.dto.PatientContactDTO;
import com.example.patient_service.dto.PatientDetailsDTO;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.service.PatientService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;

/**
 * REST Controller for managing Patient operations.
 *
 * <p>
 * Provides endpoints for creating, retrieving,
 * updating and deleting patient records.
 * </p>
 *
 * <p>
 * Base URL: <b>/api/patient-service/patients</b>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/patient-service/patients")
public class PatientController {

    private final PatientService service;

    /**
     * Constructor-based dependency injection.
     *
     * @param service patient service layer
     */
    public PatientController(PatientService service) {
        this.service = service;
    }

    /**
     * Health check endpoint.
     *
     * @return service status message
     */
    @GetMapping("/health")
    public String health() {
        return "Patient Service is running";
    }

    /**
     * Creates a new patient.
     *
     * @param details patient information
     * @return saved Patient entity
     */
    @PostMapping("/create")
    public Patient addPatient(@RequestBody PatientDetailsDTO details) {
        return service.addPatient(details);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id patient ID
     * @return ResponseEntity containing patient details
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        Patient patient = service.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    /**
     * Retrieves patient contact details by name.
     *
     * @param name patient name
     * @return list of PatientContactDTO
     */
    @GetMapping("/name/{name}")
    public List<PatientContactDTO> getByName(@PathVariable String name) {
        return service.getPatientByName(name)
                .stream()
                .map(p -> new PatientContactDTO(
                        p.getName(),
                        p.getEmail(),
                        p.getPhoneNumber()
                ))
                .toList();
    }

    /**
     * Retrieves all patients.
     *
     * @return collection of patients
     */
    @GetMapping("/all")
    public Collection<Patient> getAll() {
        return service.getAllPatients();
    }

    /**
     * Updates patient details by ID.
     *
     * @param id patient ID
     * @param details updated information
     * @return updated Patient entity
     */
    @PutMapping("/id/{id}")
    public Patient update(@PathVariable Long id,
                          @RequestBody PatientDetailsDTO details) {
        return service.updatePatientById(id, details);
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id patient ID
     * @return true if deleted, false otherwise
     */
    @DeleteMapping("/id/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return service.deletePatientById(id);
    }

    /**
     * Deletes patients by name.
     *
     * @param name patient name
     * @return true if deletion occurred, false otherwise
     */
    @DeleteMapping("/name/{name}")
    public boolean deleteByName(@PathVariable String name) {
        return service.deletePatientByName(name);
    }

    @QueryMapping
    public List <Patient> patients() {
        return service.getAllPatients().stream().toList();
    }

    @QueryMapping
    public Patient patientById(@Argument Long id) {
        return service.getPatientById(id);
    }

    @MutationMapping
    public Patient createPatient(@Argument PatientDetailsDTO patient){
        return service.addPatient(patient);
    }

    @MutationMapping
    public Patient updatePatient(@Argument Long id, @Argument PatientDetailsDTO patient){
        return service.updatePatientById(id, patient);
    }

    @MutationMapping
    public Boolean deletePatient(@Argument Long id){
        return service.deletePatientById(id);
    }
    @SubscriptionMapping
    public Flux<Patient> patientAdded() {
        return service.getPatientStream();
    }

}
