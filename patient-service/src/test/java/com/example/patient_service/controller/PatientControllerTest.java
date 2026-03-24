package com.example.patient_service.controller;

import com.example.patient_service.dto.PatientDetailsDTO;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void healthEndpointWorks() throws Exception {
        mockMvc.perform(get("/api/patient-service/patients/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patient Service is running"));
    }

    @Test
    void testCreatePatient() throws Exception {

        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setName("John");
        dto.setAge(30);
        dto.setGender("Male");
        dto.setBloodGroup("O+");
        dto.setAddress("Chennai");
        dto.setEmail("john@mail.com");
        dto.setPhoneNumber("9999999999");

        Patient patient = new Patient();
        patient.setId(1L);

        when(patientService.addPatient(any())).thenReturn(patient);

        mockMvc.perform(post("/api/patient-service/patients/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void getPatientById() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);

        when(patientService.getPatientById(1L)).thenReturn(patient);

        mockMvc.perform(get("/api/patient-service/patients/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAllPatients() throws Exception {
        when(patientService.getAllPatients())
                .thenReturn(List.of(new Patient()));

        mockMvc.perform(get("/api/patient-service/patients/all"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetByName() throws Exception {

        Patient p = new Patient();
        p.setName("John");
        p.setEmail("mail");
        p.setPhoneNumber("999");

        when(patientService.getPatientByName("John"))
                .thenReturn(List.of(p));

        mockMvc.perform(get("/api/patient-service/patients/name/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"));
    }

    @Test
    void testUpdate() throws Exception {

        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setName("Updated");
        dto.setAge(25);
        dto.setGender("Male");
        dto.setBloodGroup("O+");
        dto.setAddress("Chennai");
        dto.setEmail("updated@mail.com");
        dto.setPhoneNumber("9999999999");

        Patient patient = new Patient();
        patient.setId(1L);

        when(patientService.updatePatientById(eq(1L), any()))
                .thenReturn(patient);

        mockMvc.perform(put("/api/patient-service/patients/id/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void testDeleteByName() throws Exception {

        when(patientService.deletePatientByName("John"))
                .thenReturn(true);

        mockMvc.perform(delete("/api/patient-service/patients/name/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetById_Exception() throws Exception {

        when(patientService.getPatientById(1L))
                .thenThrow(new RuntimeException("Error"));

        mockMvc.perform(get("/api/patient-service/patients/id/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response").value("Error"));
    }

    @Test
    void deletePatientById() throws Exception {
        when(patientService.deletePatientById(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/patient-service/patients/id/1"))
                .andExpect(status().isOk());
    }
}
