package com.example.appointment_service.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {

    @Test
    void testDoctor() {
        Doctor doctor = new Doctor(
                "Dr A",
                LocalDate.now(),
                10,
                5
        );

        assertEquals("Dr A", doctor.getDoctorName());
        assertEquals(10, doctor.getTotalAppointments());
        assertNotNull(doctor.toString());
        assertEquals(doctor, doctor);
    }

    @Test
    void testAppointment() {
        Doctor doctor = new Doctor(
                "Dr A",
                LocalDate.now(),
                10,
                5
        );

        Appointment appointment = new Appointment(
                1L,
                "John",
                LocalDate.now(),
                LocalTime.NOON,
                "BOOKED",
                doctor
        );

        assertEquals("John", appointment.getPatientName());
        assertNotNull(appointment.toString());
        assertEquals(appointment, appointment);
    }

    @Test
    void testDoctorId() {
        DoctorId id1 = new DoctorId("Dr A", LocalDate.now());
        DoctorId id2 = new DoctorId("Dr A", LocalDate.now());

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }
}
