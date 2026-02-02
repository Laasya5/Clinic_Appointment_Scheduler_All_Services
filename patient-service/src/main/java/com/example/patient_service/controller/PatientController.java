package com.example.patient_service.controller;

import com.example.patient_service.dto.PatientContactDTO;
import com.example.patient_service.dto.PatientDetails;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    @Autowired
    private PatientService service;

    @GetMapping("/health")
    public String health() {
        return "Patient Service is running";
    }

    @PostMapping("/create")
    public Patient addPatient(@RequestBody PatientDetails details) {
        return service.addPatient(details);
    }

    @GetMapping("/id/{id}")
    public Patient getById(@PathVariable Long id) {
        return service.getPatientById(id);
    }

    // MULTIPLE patients returned
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

    @GetMapping("/all")
    public List<Patient> getAll() {
        return service.getAllPatients();
    }

    @PutMapping("/id/{id}")
    public Patient update(@PathVariable Long id,
                          @RequestBody PatientDetails details) {
        return service.updatePatientById(id, details);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteById(@PathVariable Long id) {
        return service.deletePatientById(id);
    }

    @DeleteMapping("/name/{name}")
    public boolean deleteByName(@PathVariable String name) {
        return service.deletePatientByName(name);
    }
}
