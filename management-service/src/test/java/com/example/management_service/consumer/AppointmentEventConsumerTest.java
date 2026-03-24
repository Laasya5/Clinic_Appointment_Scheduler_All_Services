
package com.example.management_service.consumer;

import com.example.management_service.document.*;
import com.example.management_service.event.AppointmentEvent;
import com.example.management_service.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class AppointmentEventConsumerTest {

    @Mock
    private AppointmentMongoRepository appointmentRepo;

    @Mock
    private CompletedRepo completedRepo;

    @Mock
    private CancelledRepo cancelledRepo;

    @InjectMocks
    private AppointmentEventConsumer consumer;

    private AppointmentEvent event;

    @BeforeEach
    void setup() {
        event = new AppointmentEvent(
                "Laasya",
                "Dr. Strange",
                LocalDate.now(),
                LocalTime.now(),
                "BOOKED"
        );
    }

    // ================= BOOKED =================
    @Test
    void testConsumeBooked() {
        consumer.consume(event);

        verify(appointmentRepo, times(1)).save(any(AppointmentDocument.class));
    }

    // BOOKED with Exception (catch block coverage)
    @Test
    void testConsumeBooked_Exception() {

        doThrow(new RuntimeException("DB Error"))
                .when(appointmentRepo).save(any());

        consumer.consume(event);

        verify(appointmentRepo, times(1)).save(any());
    }

    @Test
    void testConsumeUnknownStatus_NoRepoInteractions() {

        event.setStatus("INVALID");

        consumer.consume(event);

        verifyNoInteractions(completedRepo);
        verifyNoInteractions(cancelledRepo);
        verifyNoInteractions(appointmentRepo);
    }


    // ================= CANCELLED =================
    @Test
    void testConsumeCancelled() {

        event.setStatus("CANCELLED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Laasya");
        doc.setDoctorName("Dr. Strange");

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(cancelledRepo).save(any(CancelledCheckupDocument.class));
    }

    // ================= COMPLETED =================
    @Test
    void testConsumeCompleted() {

        event.setStatus("COMPLETED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Laasya");
        doc.setDoctorName("Dr. Strange");

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(completedRepo).save(any(CompletedCheckupDocument.class));
    }

    // ================= UNKNOWN =================
    @Test
    void testConsumeUnknownStatus() {

        event.setStatus("INVALID");

        consumer.consume(event);

        verifyNoInteractions(completedRepo);
        verifyNoInteractions(cancelledRepo);
    }
    @Test
    void testConsumeCancelled_NoMatchingRecords() {

        event.setStatus("CANCELLED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Other");
        doc.setDoctorName("Different");

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList()); // empty list case
        verify(cancelledRepo).save(any(CancelledCheckupDocument.class));
    }
    @Test
    void testConsumeCancelled_PartialMatch() {

        event.setStatus("CANCELLED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Laasya");  // match
        doc.setDoctorName("Different"); // no match

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(cancelledRepo).save(any(CancelledCheckupDocument.class));
    }

    @Test
    void testConsumeCompleted_NoRecords() {

        event.setStatus("COMPLETED");

        when(appointmentRepo.findAll()).thenReturn(List.of());

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(completedRepo).save(any(CompletedCheckupDocument.class));
    }

    @Test
    void testCancelled_FirstConditionFalse_ShortCircuit() {

        event.setStatus("CANCELLED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Different");   // first condition false
        doc.setDoctorName("Dr. Strange");  // second won't execute

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(cancelledRepo).save(any());
    }

    @Test
    void testCancelled_SecondConditionFalse() {

        event.setStatus("CANCELLED");

        AppointmentDocument doc = new AppointmentDocument();
        doc.setPatientName("Laasya");      // first true
        doc.setDoctorName("Different");    // second false

        when(appointmentRepo.findAll()).thenReturn(List.of(doc));

        consumer.consume(event);

        verify(cancelledRepo).save(any());
    }
    @Test
    void testConsumeBooked_Success() {

        when(appointmentRepo.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        consumer.consume(event);

        verify(appointmentRepo).save(any(AppointmentDocument.class));
    }
    @Test
    void testCancelled_EmptyList() {

        event.setStatus("CANCELLED");

        when(appointmentRepo.findAll()).thenReturn(List.of());

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
    }
    @Test
    void testCompleted_EmptyList() {

        event.setStatus("COMPLETED");

        when(appointmentRepo.findAll()).thenReturn(List.of());

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
    }
    @Test
    void testUnknownStatus_FullBranch() {

        event.setStatus("UNKNOWN");

        consumer.consume(event);

        verifyNoInteractions(appointmentRepo);
        verifyNoInteractions(cancelledRepo);
        verifyNoInteractions(completedRepo);
    }
    @Test
    void testConsume_Booked_DoesNotTriggerOthers() {

        event.setStatus("BOOKED");

        consumer.consume(event);

        verify(appointmentRepo).save(any());
        verifyNoInteractions(cancelledRepo);
        verifyNoInteractions(completedRepo);
    }
    @Test
    void testConsume_Cancelled_DoesNotTriggerOthers() {

        event.setStatus("CANCELLED");

        when(appointmentRepo.findAll()).thenReturn(List.of());

        consumer.consume(event);

        verify(cancelledRepo).save(any());
        verifyNoInteractions(completedRepo);
    }
    @Test
    void testConsume_Completed_DoesNotTriggerOthers() {

        event.setStatus("COMPLETED");

        when(appointmentRepo.findAll()).thenReturn(List.of());

        consumer.consume(event);

        verify(completedRepo).save(any());
        verifyNoInteractions(cancelledRepo);
    }
    @Test
    void testConsume_NullEventBranch() {
        try {
            consumer.consume(null);
        } catch (Exception ignored) {}
    }

    // ================= MIXED RECORDS CANCELLED =================
    @Test
    void testCancelled_MixedRecords() {

        event.setStatus("CANCELLED");

        AppointmentDocument match = new AppointmentDocument();
        match.setPatientName("Laasya");
        match.setDoctorName("Dr. Strange");

        AppointmentDocument nonMatch = new AppointmentDocument();
        nonMatch.setPatientName("Other");
        nonMatch.setDoctorName("Other");

        when(appointmentRepo.findAll())
                .thenReturn(List.of(match, nonMatch));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(cancelledRepo).save(any());
    }

    // ================= MIXED RECORDS COMPLETED =================
    @Test
    void testCompleted_MixedRecords() {

        event.setStatus("COMPLETED");

        AppointmentDocument match = new AppointmentDocument();
        match.setPatientName("Laasya");
        match.setDoctorName("Dr. Strange");

        AppointmentDocument nonMatch = new AppointmentDocument();
        nonMatch.setPatientName("Other");
        nonMatch.setDoctorName("Other");

        when(appointmentRepo.findAll())
                .thenReturn(List.of(match, nonMatch));

        consumer.consume(event);

        verify(appointmentRepo).deleteAll(anyList());
        verify(completedRepo).save(any());
    }


    @Test
    void testConsume_NullStatus() {

        event.setStatus(null);

        try {
            consumer.consume(event);
        } catch (Exception ignored) {
            // expected for null switch
        }

        verifyNoInteractions(appointmentRepo);
        verifyNoInteractions(completedRepo);
        verifyNoInteractions(cancelledRepo);
    }


}
