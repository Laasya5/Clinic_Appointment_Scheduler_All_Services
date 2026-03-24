package com.example.patient_service.service;

import com.example.patient_service.dto.PatientDetailsDTO;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.exception.NoAvailability;
import com.example.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service layer responsible for managing Patient operations.
 *
 * <p>
 * This class provides CRUD operations for {@link Patient} entity
 * including create, read, update and delete functionalities.
 * </p>
 *
 * <p>
 * It interacts with {@link PatientRepository} to perform
 * database operations.
 * </p>
 *
 * @author
 * @since 1.0
 */

@Service
public class PatientService {

    private final PatientRepository repository;

    /**
     * Constructor-based dependency injection for PatientRepository.
     *
     * @param repository the patient repository
     */
    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates and saves a new patient.
     *
     * @param details DTO containing patient information
     * @return saved Patient entity
     */
    public Patient addPatient(PatientDetailsDTO details) {
        Patient patient = new Patient();
        patient.setName(details.getName());
        patient.setAge(details.getAge());
        patient.setGender(details.getGender());
        patient.setBloodGroup(details.getBloodGroup());
        patient.setChronicConditions(details.getChronicConditions());
        patient.setPreviousIllnesses(details.getPreviousIllnesses());
        patient.setCurrentMedications(details.getCurrentMedications());
        patient.setPhoneNumber(details.getPhoneNumber());
        patient.setEmail(details.getEmail());
        patient.setAddress(details.getAddress());
        return repository.save(patient);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id unique identifier of the patient
     * @return Patient entity
     * @throws NoAvailability if no patient exists with the given ID
     */
    public Patient getPatientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new NoAvailability("Patient not found with id: " + id));
    }

    /**
     * Retrieves patients by name (case insensitive).
     *
     * @param name patient name
     * @return list of matching patients
     * @throws NoAvailability if no patients are found
     */
    public List<Patient> getPatientByName(String name) {
        List<Patient> patients = repository.findByNameIgnoreCase(name);

        if (patients.isEmpty()) {
            throw new NoAvailability(
                    "No patients found with name: " + name);
        }

        return patients;
    }

    /**
     * Retrieves all patients.
     *
     * @return collection of all patients
     */
    public Collection<Patient> getAllPatients() {
        return repository.findAll();
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id patient ID
     * @return true if patient was deleted, false if not found
     */
    public boolean deletePatientById(Long id) {
        Optional<Patient> patient = repository.findById(id);
        if (patient.isPresent()) {
            repository.delete(patient.get());
            return true;
        }
        return false;
    }

    /**
     * Deletes patients by name (case insensitive).
     *
     * @param name patient name
     * @return true if deletion occurred, false if no matching records found
     */
    public boolean deletePatientByName(String name) {
        List<Patient> patients = repository.findByNameIgnoreCase(name);
        if (patients.isEmpty()) return false;
        repository.deleteAll(patients);
        return true;
    }

    /**
     * Updates an existing patient by ID.
     *
     * Only non-null and valid fields from the DTO are updated.
     *
     * @param id patient ID
     * @param details updated patient information
     * @return updated Patient entity
     * @throws NoAvailability if patient not found
     */
    public Patient updatePatientById(Long id, PatientDetailsDTO details) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new NoAvailability("Patient not found"));

        if (details.getName() != null) patient.setName(details.getName());
        if (details.getGender() != null) patient.setGender(details.getGender());
        if (details.getAge() > 0) patient.setAge(details.getAge());
        if (details.getBloodGroup() != null) patient.setBloodGroup(details.getBloodGroup());
        if (details.getAddress() != null) patient.setAddress(details.getAddress());
        if (details.getEmail() != null) patient.setEmail(details.getEmail());
        if (details.getPhoneNumber() != null) patient.setPhoneNumber(details.getPhoneNumber());
        if (details.getChronicConditions() != null) patient.setChronicConditions(details.getChronicConditions());
        if (details.getCurrentMedications() != null) patient.setCurrentMedications(details.getCurrentMedications());
        if (details.getPreviousIllnesses() != null) patient.setPreviousIllnesses(details.getPreviousIllnesses());

        return repository.save(patient);
    }

    private final Sinks.Many<Patient> patientSink =
            Sinks.many().multicast().onBackpressureBuffer();

    public Flux<Patient> getPatientStream() {
        return patientSink.asFlux();
    }

    public Patient createPatient(Patient patient) {

        Patient saved = repository.save(patient);

        patientSink.tryEmitNext(saved); // send event

        return saved;
    }
}

