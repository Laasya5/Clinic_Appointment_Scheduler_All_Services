package com.example.appointment_service;

import com.example.appointment_service.dto.AppointmentDetails;
import com.example.appointment_service.dto.TodayAppointmentResponse;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.event.AppointmentEventPublisher;
import com.example.appointment_service.exceptions.NoAvailability;
import com.example.appointment_service.repository.AppointmentRepository;
import com.example.appointment_service.repository.DoctorRepository;
import com.example.appointment_service.service.AppointmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link AppointmentService}.
 *
 * <p>
 * This class verifies all business logic scenarios of the
 * AppointmentService including:
 * </p>
 * <ul>
 *     <li>Successful appointment booking</li>
 *     <li>Doctor not available</li>
 *     <li>No remaining slots</li>
 *     <li>Doctor busy</li>
 *     <li>Cancel operations</li>
 *     <li>Auto-complete scheduler logic</li>
 *     <li>Repository delegation methods</li>
 * </ul>
 *
 * <p>
 * Uses Mockito for mocking dependencies.
 * </p>
 *
 * @since 1.0
 */
class AppointmentServiceApplicationTests {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentEventPublisher publisher;

    @InjectMocks
    private AppointmentService appointmentService;

    /**
     * Initializes Mockito mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifies successful appointment booking.
     */
    @Test
    void testBookAppointmentSuccess() {
        AppointmentDetails details = new AppointmentDetails();
        details.setPatientName("Ravi");
        details.setDoctorName("Dr.Smith");
        details.setAppointmentDate(LocalDate.now());
        details.setAppointmentTime(LocalTime.of(10, 0));

        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr.Smith");
        doctor.setRemainingAppointments(5);

        when(doctorRepository.findById(any()))
                .thenReturn(Optional.of(doctor));

        when(appointmentRepository
                .findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
                        any(), any(), any()))
                .thenReturn(Optional.empty());

        when(appointmentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TodayAppointmentResponse response =
                appointmentService.bookAppointment(details);

        assertEquals("BOOKED", response.getStatus());
        verify(publisher).publish(any());
    }

    /**
     * Verifies exception when doctor is not available.
     */
    @Test
    void testDoctorNotAvailable() {
        when(doctorRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoAvailability.class,
                () -> appointmentService.bookAppointment(new AppointmentDetails()));
    }

    /**
     * Verifies exception when no slots are available.
     */
    @Test
    void testNoSlotsAvailable() {
        AppointmentDetails details = new AppointmentDetails();
        details.setDoctorName("Dr.Smith");
        details.setAppointmentDate(LocalDate.now());

        Doctor doctor = new Doctor();
        doctor.setRemainingAppointments(0);

        when(doctorRepository.findById(any()))
                .thenReturn(Optional.of(doctor));

        assertThrows(NoAvailability.class,
                () -> appointmentService.bookAppointment(details));
    }

    /**
     * Verifies exception when doctor is already busy at given time.
     */
    @Test
    void testDoctorBusy() {
        AppointmentDetails details = new AppointmentDetails();
        details.setDoctorName("Dr.Smith");
        details.setAppointmentDate(LocalDate.now());
        details.setAppointmentTime(LocalTime.now());

        Doctor doctor = new Doctor();
        doctor.setRemainingAppointments(5);

        when(doctorRepository.findById(any()))
                .thenReturn(Optional.of(doctor));

        when(appointmentRepository
                .findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
                        any(), any(), any()))
                .thenReturn(Optional.of(new Appointment()));

        assertThrows(NoAvailability.class,
                () -> appointmentService.bookAppointment(details));
    }

    /**
     * Verifies cancel operation when appointment is already cancelled.
     */
    @Test
    void testCancelAlreadyCancelled() {
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr.Smith");

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setStatus("CANCELLED");

        when(appointmentRepository.findByPatientNameIgnoreCase("Ravi"))
                .thenReturn(Optional.of(appointment));

        TodayAppointmentResponse response =
                appointmentService.cancelByPatientName("Ravi");

        assertEquals("CANCELLED", response.getStatus());
        verify(publisher, never()).publish(any());
    }

    /**
     * Verifies scheduler does not update already completed appointments.
     */
    @Test
    void testAutoCompleteAlreadyCompleted() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(LocalTime.now().minusHours(1));
        appointment.setStatus("COMPLETED");

        when(appointmentRepository.findAll())
                .thenReturn(List.of(appointment));

        appointmentService.autoCompleteAppointments();

        verify(appointmentRepository, never()).save(any());
    }

    /**
     * Verifies scheduler does not update cancelled appointments.
     */
    @Test
    void testAutoCompleteCancelled() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(LocalTime.now().minusHours(1));
        appointment.setStatus("CANCELLED");

        when(appointmentRepository.findAll())
                .thenReturn(List.of(appointment));

        appointmentService.autoCompleteAppointments();

        verify(appointmentRepository, never()).save(any());
    }

    /**
     * Verifies scheduler does not update future appointments.
     */
    @Test
    void testAutoCompleteFutureTime() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(LocalTime.now().plusHours(1));
        appointment.setStatus("BOOKED");

        when(appointmentRepository.findAll())
                .thenReturn(List.of(appointment));

        appointmentService.autoCompleteAppointments();

        verify(appointmentRepository, never()).save(any());
    }

    /**
     * Verifies delegation to repository for today appointments.
     */
    @Test
    void testGetTodayAppointments() {
        when(appointmentRepository.findTodayAppointments())
                .thenReturn(List.of());

        appointmentService.getTodayAppointments();

        verify(appointmentRepository).findTodayAppointments();
    }

    /**
     * Verifies delegation to repository for booked appointments.
     */
    @Test
    void testGetBookedAppointments() {
        when(appointmentRepository.findBookedAppointments())
                .thenReturn(List.of());

        appointmentService.getBookedAppointments();

        verify(appointmentRepository).findBookedAppointments();
    }

    /**
     * Verifies delegation to repository for booked appointments by date.
     */
    @Test
    void testGetBookedAppointmentsByDate() {
        when(appointmentRepository.findBookedAppointmentsByDate(any()))
                .thenReturn(List.of());

        appointmentService.getBookedAppointmentsByDate(LocalDate.now());

        verify(appointmentRepository).findBookedAppointmentsByDate(any());
    }

    /**
     * Verifies delegation to repository for upcoming appointments.
     */
    @Test
    void testGetUpcomingAppointments() {
        when(appointmentRepository.findUpcomingAppointments(any(), any()))
                .thenReturn(List.of());

        appointmentService.getUpcomingAppointments(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(5));

        verify(appointmentRepository)
                .findUpcomingAppointments(any(), any());
    }
}
