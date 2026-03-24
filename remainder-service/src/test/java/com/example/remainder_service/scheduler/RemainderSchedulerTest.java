package com.example.remainder_service.scheduler;

import com.example.remainder_service.dto.AppointmentReminderDTO;
import com.example.remainder_service.dto.PatientContactDTO;
import com.example.remainder_service.feignclient.AppointmentFeignClient;
import com.example.remainder_service.feignclient.PatientFeignClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemainderSchedulerTest {

    @Mock
    private AppointmentFeignClient appointmentClient;

    @Mock
    private PatientFeignClient patientClient;

    @InjectMocks
    private RemainderScheduler scheduler;

    private AppointmentReminderDTO appointment;
    private PatientContactDTO patient;

    @BeforeEach
    void setup() {
        appointment = new AppointmentReminderDTO();
        appointment.setPatientName("John");
        appointment.setDoctorName("Dr Smith");

        patient = new PatientContactDTO();
        patient.setName("John");
        patient.setEmail("john@test.com");
        patient.setPhone("9999999999");
    }

    //  minutesDiff < 0 branch
    @Test
    void testPastAppointmentBranch() {

        appointment.setAppointmentDate(LocalDate.now().minusDays(1));
        appointment.setAppointmentTime(LocalTime.now());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    //  24 hour TRUE branch
    @Test
    void test24HourReminderBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(1440);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        when(patientClient.getPatientByName("John"))
                .thenReturn(List.of(patient));

        scheduler.sendRemainders();

        verify(patientClient).getPatientByName("John");
    }

    //  1 hour TRUE branch
    @Test
    void test1HourReminderBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(60);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        when(patientClient.getPatientByName("John"))
                .thenReturn(List.of(patient));

        scheduler.sendRemainders();

        verify(patientClient).getPatientByName("John");
    }

    //  24 hour FALSE branch (outside range)
    @Test
    void testOutsideReminderWindow() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(200);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    @Test
    void test24HourUpperBoundFalseBranch() {

        // 1500 minutes (greater than 1445)
        LocalDateTime target = LocalDateTime.now().plusMinutes(1500);

        AppointmentReminderDTO appointment = new AppointmentReminderDTO();
        appointment.setPatientName("John");
        appointment.setDoctorName("Dr");
        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    @Test
    void test24HourLowerBoundFalseBranch() {

        // 1400 minutes (less than 1435 but <= 1445)
        LocalDateTime target = LocalDateTime.now().plusMinutes(1400);

        AppointmentReminderDTO appointment = new AppointmentReminderDTO();
        appointment.setPatientName("John");
        appointment.setDoctorName("Dr");
        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    @Test
    void test1HourLowerBoundFalseBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(50);

        AppointmentReminderDTO appointment = new AppointmentReminderDTO();
        appointment.setPatientName("John");
        appointment.setDoctorName("Dr");
        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    @Test
    void test1HourUpperBoundFalseBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(70);

        AppointmentReminderDTO appointment = new AppointmentReminderDTO();
        appointment.setPatientName("John");
        appointment.setDoctorName("Dr");
        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        scheduler.sendRemainders();

        verify(patientClient, never()).getPatientByName(any());
    }

    //  patients == null branch
    @Test
    void testPatientNullBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(60);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        when(patientClient.getPatientByName("John"))
                .thenReturn(null);

        scheduler.sendRemainders();

        verify(patientClient).getPatientByName("John");
    }

    //  patients empty branch
    @Test
    void testPatientEmptyBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(60);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        when(patientClient.getPatientByName("John"))
                .thenReturn(Collections.emptyList());

        scheduler.sendRemainders();
    }

    //  FeignException branch
    @Test
    void testFeignExceptionBranch() {

        LocalDateTime target = LocalDateTime.now().plusMinutes(60);

        appointment.setAppointmentDate(target.toLocalDate());
        appointment.setAppointmentTime(target.toLocalTime());

        when(appointmentClient.getUpcomingAppointments(24))
                .thenReturn(List.of(appointment));

        when(patientClient.getPatientByName("John"))
                .thenThrow(mock(FeignException.class));

        scheduler.sendRemainders();
    }

    // Global Exception branch
    @Test
    void testGlobalExceptionBranch() {

        when(appointmentClient.getUpcomingAppointments(24))
                .thenThrow(new RuntimeException("Error"));

        scheduler.sendRemainders();
    }
}
