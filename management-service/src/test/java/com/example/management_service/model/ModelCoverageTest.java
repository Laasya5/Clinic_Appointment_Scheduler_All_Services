package com.example.management_service.model;

import com.example.management_service.document.*;
import com.example.management_service.event.AppointmentEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ModelCoverageTest {

    @Test
    void testAppointmentEvent() {
        AppointmentEvent event = new AppointmentEvent();
        event.setPatientName("A");
        event.setDoctorName("B");
        event.setAppointmentDate(LocalDate.now());
        event.setAppointmentTime(LocalTime.now());
        event.setStatus("BOOKED");

        assertEquals("A", event.getPatientName());
    }

    @Test
    void testDocuments() {

        AppointmentDocument doc = new AppointmentDocument();
        doc.setId("1");
        doc.setPatientName("A");
        doc.setDoctorName("B");
        doc.setAppointmentDate("2025-01-01");
        doc.setAppointmentTime("10:00");
        doc.setStatus("BOOKED");

        assertEquals("A", doc.getPatientName());

        CancelledCheckupDocument cancelled = new CancelledCheckupDocument();
        cancelled.setPatientName("A");
        assertEquals("A", cancelled.getPatientName());

        CompletedCheckupDocument completed = new CompletedCheckupDocument();
        completed.setPatientName("A");
        assertEquals("A", completed.getPatientName());
    }
}
