package com.example.appointment_service;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.event.AppointmentEvent;
import com.example.appointment_service.exceptions.ExceptionResponse;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class LombokBranchCoverageTest {

    @Test
    void testAppointmentDetailsBranches() {
        AppointmentDetails a1 = new AppointmentDetails();
        AppointmentDetails a2 = new AppointmentDetails();

        assertEquals(a1, a1);         // same object
        assertNotEquals(a1, null);    // null branch
        assertNotEquals(a1, "test");  // different class

        a1.setPatientName("John");
        a2.setPatientName("Jane");

        assertNotEquals(a1, a2);      // different values
        assertNotEquals(a1.hashCode(), 0);
        assertNotNull(a1.toString());
    }

    @Test
    void testTodayAppointmentResponseBranches() {
        TodayAppointmentResponse r1 =
                new TodayAppointmentResponse("Dr","John",
                        LocalDate.now(), LocalTime.NOON,"BOOKED");

        TodayAppointmentResponse r2 =
                new TodayAppointmentResponse("Dr","John",
                        LocalDate.now(), LocalTime.NOON,"BOOKED");

        assertEquals(r1, r2);
        assertNotEquals(r1, null);
        assertNotEquals(r1, "abc");
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotNull(r1.toString());
    }

    @Test
    void testDoctorBranches() {
        Doctor d1 = new Doctor("Dr", LocalDate.now(),10,5);
        Doctor d2 = new Doctor("Dr", LocalDate.now(),10,5);

        assertEquals(d1, d2);
        assertNotEquals(d1, null);
        assertNotEquals(d1, "abc");

        d2.setDoctorName("Other");
        assertNotEquals(d1, d2);

        assertNotNull(d1.toString());
    }

    @Test
    void testAppointmentBranches() {
        Doctor doctor = new Doctor("Dr", LocalDate.now(),10,5);

        Appointment a1 =
                new Appointment(1L,"John",
                        LocalDate.now(),
                        LocalTime.NOON,"BOOKED",doctor);

        Appointment a2 =
                new Appointment(1L,"John",
                        LocalDate.now(),
                        LocalTime.NOON,"BOOKED",doctor);

        assertEquals(a1, a2);
        assertNotEquals(a1, null);
        assertNotEquals(a1, "abc");

        a2.setStatus("CANCELLED");
        assertNotEquals(a1, a2);

        assertNotNull(a1.toString());
    }

    @Test
    void testDoctorIdBranches() {
        DoctorId id1 = new DoctorId("Dr", LocalDate.now());
        DoctorId id2 = new DoctorId("Dr", LocalDate.now());

        assertEquals(id1, id2);
        assertNotEquals(id1, null);
        assertNotEquals(id1, "abc");

        id2.setDoctorName("Other");
        assertNotEquals(id1, id2);

        assertNotNull(id1.toString());
    }

    @Test
    void testAppointmentEventBranches() {
        AppointmentEvent e1 =
                new AppointmentEvent("BOOKED","John","Dr",
                        LocalDate.now(), LocalTime.NOON);

        AppointmentEvent e2 =
                new AppointmentEvent("BOOKED","John","Dr",
                        LocalDate.now(), LocalTime.NOON);

        assertEquals(e1, e2);
        assertNotEquals(e1, null);
        assertNotEquals(e1, "abc");

        e2.setStatus("CANCELLED");
        assertNotEquals(e1, e2);

        assertNotNull(e1.toString());
    }

    @Test
    void testExceptionResponseBranches() {
        ExceptionResponse e1 =
                new ExceptionResponse("Error",
                        java.time.LocalDateTime.now(),400);

        ExceptionResponse e2 =
                new ExceptionResponse("Error",
                        e1.getTime(),400);

        assertEquals(e1, e2);
        assertNotEquals(e1, null);
        assertNotEquals(e1, "abc");

        e2.setStatus(500);
        assertNotEquals(e1, e2);

        assertNotNull(e1.toString());
    }
}
