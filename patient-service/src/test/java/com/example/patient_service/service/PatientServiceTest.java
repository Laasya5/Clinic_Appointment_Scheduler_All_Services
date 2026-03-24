package com.example.patient_service.service;

import com.example.patient_service.dto.PatientDetailsDTO;
import com.example.patient_service.entity.Patient;
import com.example.patient_service.exception.NoAvailability;
import com.example.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientService service;

    private Patient patient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        patient = new Patient(
                1L,
                "John",
                30,
                "Male",
                "O+",
                List.of("Diabetes"),
                List.of("Fever"),
                List.of("Paracetamol"),
                "9999999999",
                "john@mail.com",
                "Chennai"
        );
    }

    @Test
    void testAddPatient() {
        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setName("John");
        dto.setAge(30);

        when(repository.save(any())).thenReturn(patient);

        Patient result = service.addPatient(dto);

        assertEquals("John", result.getName());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testGetPatientByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = service.getPatientById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetPatientByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailability.class,
                () -> service.getPatientById(1L));
    }

    @Test
    void testGetPatientByNameSuccess() {
        when(repository.findByNameIgnoreCase("John"))
                .thenReturn(List.of(patient));

        List<Patient> result = service.getPatientByName("John");

        assertFalse(result.isEmpty());
    }

    @Test
    void testGetPatientByNameNotFound() {
        when(repository.findByNameIgnoreCase("John"))
                .thenReturn(Collections.emptyList());

        assertThrows(NoAvailability.class,
                () -> service.getPatientByName("John"));
    }

    @Test
    void testGetAllPatients() {
        when(repository.findAll()).thenReturn(List.of(patient));

        assertEquals(1, service.getAllPatients().size());
    }

    @Test
    void testDeletePatientByIdSuccess() {
        when(repository.findById(1L)).thenReturn(Optional.of(patient));

        boolean deleted = service.deletePatientById(1L);

        assertTrue(deleted);
        verify(repository).delete(patient);
    }

    @Test
    void testDeletePatientByIdFail() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertFalse(service.deletePatientById(1L));
    }

    @Test
    void testDeletePatientByNameSuccess() {
        when(repository.findByNameIgnoreCase("John"))
                .thenReturn(List.of(patient));

        assertTrue(service.deletePatientByName("John"));
    }

    @Test
    void testDeletePatientByNameFail() {
        when(repository.findByNameIgnoreCase("John"))
                .thenReturn(Collections.emptyList());

        assertFalse(service.deletePatientByName("John"));
    }

    @Test
    void testUpdatePatientSuccess() {
        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setName("Updated");

        when(repository.findById(1L)).thenReturn(Optional.of(patient));
        when(repository.save(any())).thenReturn(patient);

        Patient result = service.updatePatientById(1L, dto);

        assertEquals("Updated", result.getName());
    }

    @Test
    void testUpdatePatientNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailability.class,
                () -> service.updatePatientById(1L, new PatientDetailsDTO()));
    }
    @Test
    void testUpdatePatient_AllFields() {

        Patient existing = new Patient();
        existing.setId(1L);

        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setName("New");
        dto.setAge(25);
        dto.setGender("Female");
        dto.setBloodGroup("A+");
        dto.setAddress("Hyd");
        dto.setEmail("mail");
        dto.setPhoneNumber("999");
        dto.setChronicConditions(List.of("Asthma"));
        dto.setCurrentMedications(List.of("Med"));
        dto.setPreviousIllnesses(List.of("Cold"));

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        service.updatePatientById(1L, dto);

        verify(repository).save(existing);
    }
    @Test
    void testUpdatePatient_AgeZero() {

        Patient existing = new Patient();
        existing.setId(1L);
        existing.setAge(50);

        PatientDetailsDTO dto = new PatientDetailsDTO();
        dto.setAge(0); // should NOT update

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        Patient result = service.updatePatientById(1L, dto);

        assertEquals(50, result.getAge());
    }

}
