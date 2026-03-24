package com.example.appointment_service.service;

import com.example.appointment_service.entity.*;
import com.example.appointment_service.repository.DoctorRepository;
import com.example.appointment_service.exceptions.NoAvailability;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    DoctorRepository repo = mock(DoctorRepository.class);
    DoctorService service = new DoctorService(repo);

    @Test
    void testCreateDoctor() {
        Doctor doctor = new Doctor("Dr A",
                LocalDate.now(),10,10);

        when(repo.save(doctor)).thenReturn(doctor);

        assertEquals(doctor, service.createDoctor(doctor));
    }

    @Test
    void testGetDoctorSuccess() {
        Doctor doctor = new Doctor("Dr A",
                LocalDate.now(),10,10);

        when(repo.findById(any()))
                .thenReturn(Optional.of(doctor));

        assertNotNull(service.getDoctor("Dr A",
                LocalDate.now()));
    }

    @Test
    void testGetDoctorNotFound() {
        when(repo.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoAvailability.class,
                () -> service.getDoctor("Dr A",
                        LocalDate.now()));
    }
}
