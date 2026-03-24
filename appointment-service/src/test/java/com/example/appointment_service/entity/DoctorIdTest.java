package com.example.appointment_service.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DoctorIdTest {

    @Test
    void doctorIdFullBranchCoverage() {

        LocalDate date = LocalDate.now();

        DoctorId id1 = new DoctorId("Dr", date);
        DoctorId id2 = new DoctorId("Dr", date);

        // equal objects
        assertEquals(id1, id2);

        // change doctorName
        id2.setDoctorName("Other");
        assertNotEquals(id1, id2);

        // change availabilityDate
        id2 = new DoctorId("Dr", date.plusDays(1));
        assertNotEquals(id1, id2);

        // null comparison
        assertNotEquals(null, id1);

        // different class comparison
        assertNotEquals("abc", id1);

        // hashCode
        assertTrue(id1.hashCode() != 0);

        // toString
        assertNotNull(id1.toString());
    }
}
