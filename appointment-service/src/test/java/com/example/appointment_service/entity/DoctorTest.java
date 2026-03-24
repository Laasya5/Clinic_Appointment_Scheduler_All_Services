package com.example.appointment_service.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {

    @Test
    void doctorFullBranchCoverage() {

        LocalDate date = LocalDate.now();

        Doctor d1 = new Doctor("Dr", date, 10, 5);
        Doctor d2 = new Doctor("Dr", date, 10, 5);

        assertEquals(d1, d2);

        d2.setDoctorName("Other");
        assertNotEquals(d1, d2);

        d2 = new Doctor("Dr", date.plusDays(1), 10, 5);
        assertNotEquals(d1, d2);

        d2 = new Doctor("Dr", date, 20, 5);
        assertNotEquals(d1, d2);

        d2 = new Doctor("Dr", date, 10, 1);
        assertNotEquals(d1, d2);

        assertNotEquals(null, d1);
        assertNotEquals("abc", d1);

        assertTrue(d1.hashCode() != 0);
        assertNotNull(d1.toString());
    }

}
