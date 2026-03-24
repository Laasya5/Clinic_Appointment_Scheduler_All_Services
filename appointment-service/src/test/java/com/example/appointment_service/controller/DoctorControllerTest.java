package com.example.appointment_service.controller;

import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.security.JwtFilter;
import com.example.appointment_service.security.JwtUtil;
import com.example.appointment_service.service.DoctorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService service;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;

    // ---------- CREATE ----------

    @Test
    void testCreateDoctor() throws Exception {

        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr.Smith");
        doctor.setAvailabilityDate(LocalDate.now());
        doctor.setTotalAppointments(10);
        doctor.setRemainingAppointments(10);

        when(service.createDoctor(any(Doctor.class)))
                .thenReturn(doctor);

        mockMvc.perform(post("/api/appointment-service/doctors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "doctorName":"Dr.Smith",
                              "availabilityDate":"2026-01-01",
                              "totalAppointments":10,
                              "remainingAppointments":10
                            }
                        """))
                .andExpect(status().isOk());
    }

    // ---------- GET ----------

    @Test
    void testGetDoctor() throws Exception {

        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr.Smith");
        doctor.setAvailabilityDate(LocalDate.now());

        when(service.getDoctor(anyString(), any(LocalDate.class)))
                .thenReturn(doctor);

        mockMvc.perform(get("/api/appointment-service/doctors")
                        .param("doctorName", "Dr.Smith")
                        .param("availabilityDate", "2026-01-01"))
                .andExpect(status().isOk());
    }
}
