package com.example.appointment_service;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.event.AppointmentEvent;
import com.example.appointment_service.exceptions.ExceptionResponse;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class FullLombokBranchTest {

    @Test
    void coverAppointmentDetailsAllBranches() {

        AppointmentDetails a1 = new AppointmentDetails();
        AppointmentDetails a2 = new AppointmentDetails();

        // same reference
        assertTrue(a1.equals(a1));

        // null
        assertFalse(a1.equals(null));

        // different class
        assertFalse(a1.equals("string"));

        // both empty objects
        assertTrue(a1.equals(a2));

        // partial difference
        a1.setPatientName("John");
        assertFalse(a1.equals(a2));

        a2.setPatientName("John");
        assertTrue(a1.equals(a2));

        a1.setDoctorName("Dr A");
        assertFalse(a1.equals(a2));

        a2.setDoctorName("Dr A");
        assertTrue(a1.equals(a2));

        assertNotNull(a1.hashCode());
        assertNotNull(a1.toString());
    }

    @Test
    void coverDoctorAllBranches() {

        LocalDate date = LocalDate.now();

        Doctor d1 = new Doctor("Dr", date,10,5);
        Doctor d2 = new Doctor("Dr", date,10,5);

        assertTrue(d1.equals(d2));

        d2.setRemainingAppointments(3);
        assertFalse(d1.equals(d2));

        d2.setRemainingAppointments(5);
        assertTrue(d1.equals(d2));

        assertFalse(d1.equals(null));
        assertFalse(d1.equals("abc"));
        assertNotNull(d1.hashCode());
    }

    @Test
    void coverAppointmentAllBranches() {

        Doctor doctor = new Doctor("Dr",
                LocalDate.now(),10,5);

        Appointment a1 =
                new Appointment(1L,"John",
                        LocalDate.now(),
                        LocalTime.NOON,"BOOKED",doctor);

        Appointment a2 =
                new Appointment(1L,"John",
                        LocalDate.now(),
                        LocalTime.NOON,"BOOKED",doctor);

        assertTrue(a1.equals(a2));

        a2.setStatus("CANCELLED");
        assertFalse(a1.equals(a2));

        a2.setStatus("BOOKED");
        a2.setPatientName("Jane");
        assertFalse(a1.equals(a2));

        assertFalse(a1.equals(null));
        assertFalse(a1.equals("abc"));

        assertNotNull(a1.hashCode());
    }

    @Test
    void coverDoctorIdBranches() {

        DoctorId id1 =
                new DoctorId("Dr", LocalDate.now());
        DoctorId id2 =
                new DoctorId("Dr", LocalDate.now());

        assertTrue(id1.equals(id2));

        id2.setDoctorName("Other");
        assertFalse(id1.equals(id2));

        assertFalse(id1.equals(null));
        assertFalse(id1.equals("abc"));

        assertNotNull(id1.hashCode());
    }

    @Test
    void coverEventBranches() {

        AppointmentEvent e1 =
                new AppointmentEvent("BOOKED","John","Dr",
                        LocalDate.now(), LocalTime.NOON);

        AppointmentEvent e2 =
                new AppointmentEvent("BOOKED","John","Dr",
                        LocalDate.now(), LocalTime.NOON);

        assertTrue(e1.equals(e2));

        e2.setStatus("CANCELLED");
        assertFalse(e1.equals(e2));

        assertFalse(e1.equals(null));
        assertFalse(e1.equals("abc"));
        assertNotNull(e1.hashCode());
    }

    @Test
    void coverExceptionResponseBranches() {

        ExceptionResponse ex1 =
                new ExceptionResponse("Error",
                        LocalDateTime.now(),400);

        ExceptionResponse ex2 =
                new ExceptionResponse("Error",
                        ex1.getTime(),400);

        assertTrue(ex1.equals(ex2));

        ex2.setStatus(500);
        assertFalse(ex1.equals(ex2));

        assertFalse(ex1.equals(null));
        assertFalse(ex1.equals("abc"));

        assertNotNull(ex1.hashCode());
    }
}
