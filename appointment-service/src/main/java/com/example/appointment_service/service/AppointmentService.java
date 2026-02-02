package com.example.appointment_service.service;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.repository.*;
import com.example.appointment_service.exceptions.NoAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;



    public Appointment bookAppointment(AppointmentDetails details) {

        Doctor doctor = doctorRepository.findById(
                new DoctorId(details.getDoctorName(), details.getAppointmentDate())
        ).orElseThrow(() -> new NoAvailability("Doctor not available"));

        if (doctor.getRemainingAppointments() <= 0)
            throw new NoAvailability("No slots available");

        boolean busy = appointmentRepository
                .findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTime(
                        details.getDoctorName(),
                        details.getAppointmentDate(),
                        details.getAppointmentTime()
                ).isPresent();

        if (busy) throw new NoAvailability("Doctor busy at this time");

        Appointment appointment = new Appointment();
        appointment.setPatientName(details.getPatientName());
        appointment.setAppointmentDate(details.getAppointmentDate());
        appointment.setAppointmentTime(details.getAppointmentTime());
        appointment.setStatus("BOOKED");
        appointment.setDoctor(doctor);

        doctor.setRemainingAppointments(doctor.getRemainingAppointments() - 1);
        doctorRepository.save(doctor);

        return appointmentRepository.save(appointment);
    }

    public Appointment getByPatientName(String patientName) {
        return appointmentRepository.findByPatientNameIgnoreCase(patientName)
                .orElseThrow(() -> new NoAvailability("Patient not found"));
    }

    public Appointment cancelByPatientName(String patientName) {
        Appointment appointment = getByPatientName(patientName);

        if ("CANCELLED".equals(appointment.getStatus()))
            return appointment;

        appointment.setStatus("CANCELLED");
        Doctor doctor = appointment.getDoctor();
        doctor.setRemainingAppointments(doctor.getRemainingAppointments() + 1);
        doctorRepository.save(doctor);

        return appointmentRepository.save(appointment);
    }

//    public void deleteExpiredAppointments() {
//        appointmentRepository.deleteByAppointmentDateTimeBefore(LocalDateTime.now());
//    }


    public List<TodayAppointmentResponse> getTodayAppointments() {
        return appointmentRepository.findTodayAppointments();
    }

    public List<TodayAppointmentResponse> getBookedAppointments() {
        return appointmentRepository.findBookedAppointments();
    }

    public List<TodayAppointmentResponse> getBookedAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findBookedAppointmentsByDate(date);
    }

//    public List<TodayAppointmentResponse> getUpcomingAppointments(
//            LocalDateTime from,
//            LocalDateTime to) {
//
//        return appointmentRepository.findUpcomingAppointments(
//                from.toLocalDate(),
//                from.toLocalTime(),
//                to.toLocalDate(),
//                to.toLocalTime()
//        );
//    }
public List<TodayAppointmentResponse> getUpcomingAppointments(
        LocalDateTime from,
        LocalDateTime to) {

    return appointmentRepository.findUpcomingAppointments(
            from.toLocalDate(),
            to.toLocalDate()
    );
}

}
