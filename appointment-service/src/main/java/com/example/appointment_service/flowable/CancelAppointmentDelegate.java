package com.example.appointment_service.flowable;

import com.example.appointment_service.entity.Appointment;
import com.example.appointment_service.repository.AppointmentRepository;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("cancelAppointmentDelegate")
public class CancelAppointmentDelegate implements JavaDelegate {

    private final AppointmentRepository appointmentRepository;

    public CancelAppointmentDelegate(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void execute(DelegateExecution execution) {

        Long appointmentId = (Long) execution.getVariable("appointmentId");

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("CANCELLED");

        appointmentRepository.save(appointment);

        execution.setVariable("doctorName",
                appointment.getDoctor().getDoctorName());

        execution.setVariable("patientName",
                appointment.getPatientName());

        execution.setVariable("appointmentDate",
                appointment.getAppointmentDate());

        execution.setVariable("appointmentTime",
                appointment.getAppointmentTime());
        System.out.println("DELEGATE EXECUTED");
    }

}