package com.example.appointment_service.event;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentEventTest {

    @Test
    void fullBranchCoverageIncludingNulls() {

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.NOON;

        AppointmentEvent base =
                new AppointmentEvent("BOOKED","John","Dr",date,time);

        // equal
        AppointmentEvent same =
                new AppointmentEvent("BOOKED","John","Dr",date,time);
        assertEquals(base, same);

        // different fields one by one
        assertNotEquals(base,
                new AppointmentEvent("CANCELLED","John","Dr",date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","Jane","Dr",date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John","DrX",date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John","Dr",date.plusDays(1),time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John","Dr",date,LocalTime.MIDNIGHT));

        // 🔥 NULL FIELD CASES (VERY IMPORTANT)

        assertNotEquals(base,
                new AppointmentEvent(null,"John","Dr",date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED",null,"Dr",date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John",null,date,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John","Dr",null,time));

        assertNotEquals(base,
                new AppointmentEvent("BOOKED","John","Dr",date,null));

        // base with nulls compared to normal
        AppointmentEvent nullEvent =
                new AppointmentEvent(null,null,null,null,null);

        assertNotEquals(nullEvent, base);
        assertNotEquals(base, nullEvent);

        // compare null object
        assertNotEquals(base, null);

        // compare different type
        assertNotEquals(base, "abc");

        // toString + hash
        assertNotNull(base.toString());
        assertTrue(base.hashCode() != 0);
    }
    @Test
    void coverCanEqualBranch() {

        class AppointmentEventChild extends AppointmentEvent {
            AppointmentEventChild() {
                super();
            }
        }

        AppointmentEvent parent =
                new AppointmentEvent("BOOKED", "John", "Dr",
                        java.time.LocalDate.now(),
                        java.time.LocalTime.NOON);

        AppointmentEventChild child =
                new AppointmentEventChild();

        // this forces canEqual() branch
        assertNotEquals(parent, child);
    }
    @Test
    void selfComparisonBranch() {
        AppointmentEvent event =
                new AppointmentEvent("BOOKED","John","Dr",
                        java.time.LocalDate.now(),
                        java.time.LocalTime.NOON);

        assertEquals(event, event); // triggers (o == this)
    }
    @Test
    void coverAllNullCombinations() {

        AppointmentEvent base =
                new AppointmentEvent("BOOKED","John","Dr",
                        java.time.LocalDate.now(),
                        java.time.LocalTime.NOON);

        AppointmentEvent allNull =
                new AppointmentEvent(null,null,null,null,null);

        // base vs all null
        assertNotEquals(base, allNull);

        // all null vs base
        assertNotEquals(allNull, base);

        // both null objects
        AppointmentEvent allNull2 =
                new AppointmentEvent(null,null,null,null,null);

        assertEquals(allNull, allNull2);
    }
    @Test
    void crossNullFieldCombinations() {

        java.time.LocalDate date = java.time.LocalDate.now();
        java.time.LocalTime time = java.time.LocalTime.NOON;

        AppointmentEvent normal =
                new AppointmentEvent("BOOKED","John","Dr",date,time);

        // status: non-null vs null
        AppointmentEvent statusNull =
                new AppointmentEvent(null,"John","Dr",date,time);
        assertNotEquals(normal, statusNull);
        assertNotEquals(statusNull, normal);

        // patientName: non-null vs null
        AppointmentEvent patientNull =
                new AppointmentEvent("BOOKED",null,"Dr",date,time);
        assertNotEquals(normal, patientNull);
        assertNotEquals(patientNull, normal);

        // doctorName
        AppointmentEvent doctorNull =
                new AppointmentEvent("BOOKED","John",null,date,time);
        assertNotEquals(normal, doctorNull);
        assertNotEquals(doctorNull, normal);

        // appointmentDate
        AppointmentEvent dateNull =
                new AppointmentEvent("BOOKED","John","Dr",null,time);
        assertNotEquals(normal, dateNull);
        assertNotEquals(dateNull, normal);

        // appointmentTime
        AppointmentEvent timeNull =
                new AppointmentEvent("BOOKED","John","Dr",date,null);
        assertNotEquals(normal, timeNull);
        assertNotEquals(timeNull, normal);

        // both-null same field equality
        AppointmentEvent bothNull1 =
                new AppointmentEvent(null,null,null,null,null);

        AppointmentEvent bothNull2 =
                new AppointmentEvent(null,null,null,null,null);

        assertEquals(bothNull1, bothNull2);
    }

}
