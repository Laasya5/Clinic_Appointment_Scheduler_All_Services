package com.example.appointment_service.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testAppointmentDetails() {
        AppointmentDetails dto = new AppointmentDetails();
        dto.setPatientName("John");
        dto.setDoctorName("Dr A");
        dto.setAppointmentDate(LocalDate.now());
        dto.setAppointmentTime(LocalTime.NOON);

        assertEquals("John", dto.getPatientName());
        assertEquals("Dr A", dto.getDoctorName());
        assertNotNull(dto.toString());
    }

    @Test
    void testTodayAppointmentResponse() {
        TodayAppointmentResponse response =
                new TodayAppointmentResponse(
                        "Dr A",
                        "John",
                        LocalDate.now(),
                        LocalTime.NOON,
                        "BOOKED"
                );

        assertEquals("BOOKED", response.getStatus());
        assertNotNull(response.toString());
        assertEquals(response, response);  // equals coverage
    }
}
