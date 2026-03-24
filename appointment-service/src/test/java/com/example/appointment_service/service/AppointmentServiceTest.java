package com.example.appointment_service.service;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.event.AppointmentEventPublisher;
import com.example.appointment_service.exceptions.NoAvailability;
import com.example.appointment_service.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

	AppointmentRepository appointmentRepository;
	DoctorRepository doctorRepository;
	AppointmentEventPublisher publisher;
	AppointmentService service;

	@BeforeEach
	void setup() {
		appointmentRepository = mock(AppointmentRepository.class);
		doctorRepository = mock(DoctorRepository.class);
		publisher = mock(AppointmentEventPublisher.class);

		service = new AppointmentService(
				appointmentRepository,
				doctorRepository,
				publisher
		);
	}

	@Test
	void testBookAppointmentSuccess() {

		AppointmentDetails details = new AppointmentDetails();
		details.setDoctorName("Dr A");
		details.setPatientName("John");
		details.setAppointmentDate(LocalDate.now());
		details.setAppointmentTime(LocalTime.NOON);

		Doctor doctor = new Doctor("Dr A",
				details.getAppointmentDate(),5,5);

		when(doctorRepository.findById(any()))
				.thenReturn(Optional.of(doctor));

		when(appointmentRepository
				.findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
						any(),any(),any()))
				.thenReturn(Optional.empty());

		when(appointmentRepository.save(any()))
				.thenAnswer(i -> i.getArguments()[0]);

		TodayAppointmentResponse response =
				service.bookAppointment(details);

		assertEquals("BOOKED", response.getStatus());
		verify(publisher, times(1)).publish(any());
	}

	@Test
	void testDoctorUnavailable() {
		when(doctorRepository.findById(any()))
				.thenReturn(Optional.empty());

		assertThrows(NoAvailability.class,
				() -> service.bookAppointment(
						new AppointmentDetails()));
	}

	@Test
	void testCancelAlreadyCancelled() {

		Doctor doctor =
				new Doctor("Dr A",
						LocalDate.now(),5,5);

		Appointment appointment =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.NOON,
						"CANCELLED",doctor);

		when(appointmentRepository
				.findByPatientNameIgnoreCase("John"))
				.thenReturn(Optional.of(appointment));

		TodayAppointmentResponse response =
				service.cancelByPatientName("John");

		assertEquals("CANCELLED", response.getStatus());
	}

	@Test
	void testAutoCompleteAlreadyCompleted() {

		Doctor doctor =
				new Doctor("Dr A",
						LocalDate.now(),5,5);

		Appointment appointment =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.MIN,
						"COMPLETED",doctor);

		when(appointmentRepository.findAll())
				.thenReturn(List.of(appointment));

		service.autoCompleteAppointments();

		verify(publisher, never()).publish(any());
	}

	@Test
	void testNoSlots() {

		AppointmentDetails details = new AppointmentDetails();
		details.setDoctorName("Dr A");
		details.setAppointmentDate(LocalDate.now());

		Doctor doctor = new Doctor("Dr A",
				LocalDate.now(),5,0);

		when(doctorRepository.findById(any()))
				.thenReturn(Optional.of(doctor));

		assertThrows(NoAvailability.class,
				() -> service.bookAppointment(details));
	}

	@Test
	void testDoctorBusy() {

		AppointmentDetails details = new AppointmentDetails();
		details.setDoctorName("Dr A");
		details.setAppointmentDate(LocalDate.now());
		details.setAppointmentTime(LocalTime.NOON);

		Doctor doctor = new Doctor("Dr A",
				LocalDate.now(),5,5);

		when(doctorRepository.findById(any()))
				.thenReturn(Optional.of(doctor));

		when(appointmentRepository
				.findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
						any(),any(),any()))
				.thenReturn(Optional.of(new Appointment()));

		assertThrows(NoAvailability.class,
				() -> service.bookAppointment(details));
	}

	@Test
	void testGetByPatientSuccess() {

		Appointment appointment = new Appointment(
				1L,"John",LocalDate.now(),
				LocalTime.NOON,"BOOKED",
				new Doctor("Dr A",LocalDate.now(),5,5));

		when(appointmentRepository
				.findByPatientNameIgnoreCase("John"))
				.thenReturn(Optional.of(appointment));

		assertEquals("John",
				service.getByPatientName("John")
						.getPatientName());
	}

	@Test
	void testCancelAppointment() {

		Doctor doctor = new Doctor("Dr A",
				LocalDate.now(),5,2);

		Appointment appointment =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.NOON,
						"BOOKED",doctor);

		when(appointmentRepository
				.findByPatientNameIgnoreCase("John"))
				.thenReturn(Optional.of(appointment));

		when(appointmentRepository.save(any()))
				.thenAnswer(i -> i.getArguments()[0]);

		TodayAppointmentResponse response =
				service.cancelByPatientName("John");

		assertEquals("CANCELLED",
				response.getStatus());
	}

	@Test
	void testAutoCompleteAppointments() {

		Doctor doctor =
				new Doctor("Dr A",
						LocalDate.now(),5,5);

		Appointment appointment =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.MIN,
						"BOOKED",doctor);

		when(appointmentRepository.findAll())
				.thenReturn(List.of(appointment));

		when(appointmentRepository.save(any()))
				.thenAnswer(i -> i.getArguments()[0]);

		service.autoCompleteAppointments();

		verify(publisher, atLeastOnce())
				.publish(any());
	}

	@Test
	void testGetTodayAppointments() {
		when(appointmentRepository.findTodayAppointments())
				.thenReturn(new ArrayList<>());
		assertNotNull(service.getTodayAppointments());
	}

	@Test
	void testGetBookedAppointments() {
		when(appointmentRepository.findBookedAppointments())
				.thenReturn(new ArrayList<>());
		assertNotNull(service.getBookedAppointments());
	}

	@Test
	void testGetBookedAppointmentsByDate() {
		when(appointmentRepository
				.findBookedAppointmentsByDate(any()))
				.thenReturn(new ArrayList<>());
		assertNotNull(service
				.getBookedAppointmentsByDate(LocalDate.now()));
	}

	@Test
	void testUpcomingAppointments() {
		when(appointmentRepository
				.findUpcomingAppointments(any(),any()))
				.thenReturn(new ArrayList<>());
		assertNotNull(service
				.getUpcomingAppointments(
						LocalDateTime.now(),
						LocalDateTime.now().plusHours(2)));
	}
	@Test
	void autoComplete_AllBranchesCovered() {

		Doctor doctor =
				new Doctor("Dr",
						LocalDate.now(),5,5);

		// Case 1: should complete
		Appointment a1 =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.MIN,
						"BOOKED",doctor);

		// Case 2: already completed
		Appointment a2 =
				new Appointment(2L,"Jane",
						LocalDate.now(),
						LocalTime.MIN,
						"COMPLETED",doctor);

		// Case 3: cancelled
		Appointment a3 =
				new Appointment(3L,"Alex",
						LocalDate.now(),
						LocalTime.MIN,
						"CANCELLED",doctor);

		// Case 4: future time
		Appointment a4 =
				new Appointment(4L,"Bob",
						LocalDate.now(),
						LocalTime.MAX,
						"BOOKED",doctor);

		when(appointmentRepository.findAll())
				.thenReturn(List.of(a1, a2, a3, a4));

		when(appointmentRepository.save(any()))
				.thenAnswer(i -> i.getArguments()[0]);

		service.autoCompleteAppointments();

		// should publish only once (for a1)
		verify(publisher, times(1)).publish(any());
	}

	@Test
	void cancelAlreadyCancelledBranch() {

		Doctor doctor =
				new Doctor("Dr",
						LocalDate.now(),5,5);

		Appointment appointment =
				new Appointment(1L,"John",
						LocalDate.now(),
						LocalTime.NOON,
						"CANCELLED",doctor);

		when(appointmentRepository
				.findByPatientNameIgnoreCase("John"))
				.thenReturn(Optional.of(appointment));

		TodayAppointmentResponse response =
				service.cancelByPatientName("John");

		assertEquals("CANCELLED", response.getStatus());

		verify(publisher, never()).publish(any());
	}

}
