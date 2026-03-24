package com.example.appointment_service.controller;

import com.example.appointment_service.dto.AppointmentDetails;
import com.example.appointment_service.dto.TodayAppointmentResponse;
import com.example.appointment_service.security.JwtFilter;
import com.example.appointment_service.security.JwtUtil;
import com.example.appointment_service.service.AppointmentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false) // DISABLE SECURITY FILTERS

class AppointmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService service;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtUtil jwtUtil;
    // ---------- BOOK ----------

    @Test
    void testBook() throws Exception {

        TodayAppointmentResponse response =
                new TodayAppointmentResponse(
                        "Dr.Smith",
                        "Ravi",
                        LocalDate.now(),
                        LocalTime.now(),
                        "BOOKED"
                );

        when(service.bookAppointment(any(AppointmentDetails.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/appointment-service/appointments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "patientName":"Ravi",
                              "doctorName":"Dr.Smith",
                              "appointmentDate":"2026-01-01",
                              "appointmentTime":"10:00:00"
                            }
                        """))
                .andExpect(status().isOk());
    }

    // ---------- SEARCH ----------

    @Test
    void testGetByPatient() throws Exception {

        when(service.getByPatientName("Ravi"))
                .thenReturn(new TodayAppointmentResponse(
                        "Dr.Smith",
                        "Ravi",
                        LocalDate.now(),
                        LocalTime.now(),
                        "BOOKED"
                ));

        mockMvc.perform(get("/api/appointment-service/appointments/search/Ravi"))
                .andExpect(status().isOk());
    }

    // ---------- CANCEL ----------

    @Test
    void testCancel() throws Exception {

        when(service.cancelByPatientName("Ravi"))
                .thenReturn(new TodayAppointmentResponse(
                        "Dr.Smith",
                        "Ravi",
                        LocalDate.now(),
                        LocalTime.now(),
                        "CANCELLED"
                ));

        mockMvc.perform(delete("/api/appointment-service/appointments/cancel/Ravi"))
                .andExpect(status().isOk());
    }

    // ---------- TODAY ----------

    @Test
    void testToday() throws Exception {

        when(service.getTodayAppointments())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/appointment-service/appointments/today"))
                .andExpect(status().isOk());
    }

    // ---------- BOOKED ----------

    @Test
    void testBooked() throws Exception {

        when(service.getBookedAppointments())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/appointment-service/appointments/booked"))
                .andExpect(status().isOk());
    }

    // ---------- BOOKED BY DATE ----------

    @Test
    void testBookedByDate() throws Exception {

        when(service.getBookedAppointmentsByDate(any(LocalDate.class)))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/appointment-service/appointments/booked/date")
                        .param("date", "2026-01-01"))
                .andExpect(status().isOk());
    }

    // ---------- UPCOMING ----------

    @Test
    void testUpcoming() throws Exception {

        when(service.getUpcomingAppointments(any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/appointment-service/appointments/upcoming")
                        .param("hours", "5"))
                .andExpect(status().isOk());
    }
}
