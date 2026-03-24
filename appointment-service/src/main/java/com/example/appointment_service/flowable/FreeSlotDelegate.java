package com.example.appointment_service.flowable;

import com.example.appointment_service.entity.Doctor;
import com.example.appointment_service.entity.DoctorId;
import com.example.appointment_service.repository.DoctorRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component("freeSlotDelegate")
public class FreeSlotDelegate implements JavaDelegate {

    private final DoctorRepository doctorRepository;

    public FreeSlotDelegate(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        String doctorName = (String) execution.getVariable("doctorName");
        LocalDate date = (LocalDate) execution.getVariable("appointmentDate");

        Doctor doctor = doctorRepository
                .findById(new DoctorId(doctorName, date))
                .orElseThrow();

        doctor.setRemainingAppointments(
                doctor.getRemainingAppointments() + 1
        );

        doctorRepository.save(doctor);
    }
}