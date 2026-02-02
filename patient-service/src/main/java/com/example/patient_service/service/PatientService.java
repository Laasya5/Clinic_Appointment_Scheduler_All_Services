package com.example.patient_service.service;

import com.example.patient_service.dto.PatientDetails;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.exception.NoAvailability;
import com.example.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repository;

    // CREATE
    public Patient addPatient(PatientDetails details) {
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

    // GET BY ID
    public Patient getPatientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoAvailability("Patient not found"));
    }

    // GET BY NAME (MULTIPLE)
    public List<Patient> getPatientByName(String name) {
        List<Patient> patients = repository.findByNameIgnoreCase(name);
        if (patients.isEmpty()) {
            throw new NoAvailability("Patient not found");
        }
        return patients;
    }

    // GET ALL
    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    // DELETE BY ID
    public boolean deletePatientById(Long id) {
        Patient patient = getPatientById(id);
        repository.delete(patient);
        return true;
    }

    // DELETE BY NAME (ALL WITH SAME NAME)
    public boolean deletePatientByName(String name) {
        List<Patient> patients = getPatientByName(name);
        repository.deleteAll(patients);
        return true;
    }

    // UPDATE BY ID
    public Patient updatePatientById(Long id, PatientDetails details) {
        Patient patient = getPatientById(id);

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
}
