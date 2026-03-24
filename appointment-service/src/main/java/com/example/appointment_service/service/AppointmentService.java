package com.example.appointment_service.service;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.*;
import com.example.appointment_service.event.AppointmentEvent;
import com.example.appointment_service.event.AppointmentEventPublisher;
import com.example.appointment_service.repository.*;
import com.example.appointment_service.exceptions.NoAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static final String BOOKED = "BOOKED";
    private static final String CANCELLED = "CANCELLED";
    private static final String COMPLETED = "COMPLETED";

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentEventPublisher publisher;


    public TodayAppointmentResponse bookAppointment(AppointmentDetails details) {

        Doctor doctor = doctorRepository.findById(
                new DoctorId(details.getDoctorName(), details.getAppointmentDate())
        ).orElseThrow(() -> new NoAvailability("Doctor not available"));

        if (doctor.getRemainingAppointments() <= 0)
            throw new NoAvailability("No slots available");


        boolean busy = appointmentRepository
                .findByDoctor_DoctorNameAndAppointmentDateAndAppointmentTimeAndStatusIn(
                        details.getDoctorName(),
                        details.getAppointmentDate(),
                        details.getAppointmentTime(),
                        List.of(BOOKED)
                ).isPresent();

        if (busy)
            throw new NoAvailability("Doctor busy at this time");

        Appointment appointment = new Appointment();
        appointment.setPatientName(details.getPatientName());
        appointment.setAppointmentDate(details.getAppointmentDate());
        appointment.setAppointmentTime(details.getAppointmentTime());
        appointment.setStatus(BOOKED);
        appointment.setDoctor(doctor);

        doctor.setRemainingAppointments(doctor.getRemainingAppointments() - 1);
        doctorRepository.save(doctor);

        Appointment saved = appointmentRepository.save(appointment);

        publisher.publish(new AppointmentEvent(
                BOOKED,
                saved.getPatientName(),
                saved.getDoctor().getDoctorName(),
                saved.getAppointmentDate(),
                saved.getAppointmentTime()
        ));

        return mapToResponse(saved);
    }


    public TodayAppointmentResponse cancelByAppointmentId(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() -> new NoAvailability("Appointment not found"));

        if (CANCELLED.equals(appointment.getStatus())) {
            return mapToResponse(appointment);
        }

        appointment.setStatus(CANCELLED);

        Doctor doctor = appointment.getDoctor();
        doctor.setRemainingAppointments(
                doctor.getRemainingAppointments() + 1
        );

        doctorRepository.save(doctor);
        Appointment saved = appointmentRepository.save(appointment);

        publisher.publish(new AppointmentEvent(
                CANCELLED,
                saved.getPatientName(),
                saved.getDoctor().getDoctorName(),
                saved.getAppointmentDate(),
                saved.getAppointmentTime()
        ));

        return mapToResponse(saved);
    }

    // =========================
    // AUTO COMPLETE
    // =========================

    @Scheduled(cron = "0 * * * * *")
    public void autoCompleteAppointments() {

        List<Appointment> appointments = appointmentRepository.findAll();

        for (Appointment appointment : appointments) {

            if (appointment.getAppointmentTime().isBefore(LocalTime.now())
                    && BOOKED.equals(appointment.getStatus())) {

                appointment.setStatus(COMPLETED);

                Appointment saved = appointmentRepository.save(appointment);

                publisher.publish(new AppointmentEvent(
                        COMPLETED,
                        saved.getPatientName(),
                        saved.getDoctor().getDoctorName(),
                        saved.getAppointmentDate(),
                        saved.getAppointmentTime()
                ));
            }
        }
    }

    public TodayAppointmentResponse getByPatientName(String patientName) {

        Appointment appointment = appointmentRepository
                .findByPatientNameIgnoreCase(patientName)
                .orElseThrow(() -> new NoAvailability("Patient not found"));

        return mapToResponse(appointment);
    }

    public List<TodayAppointmentResponse> getTodayAppointments() {
        return appointmentRepository.findTodayAppointments();
    }

    public List<TodayAppointmentResponse> getBookedAppointments() {
        return appointmentRepository.findBookedAppointments();
    }

    public List<TodayAppointmentResponse> getBookedAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findBookedAppointmentsByDate(date);
    }

    public List<TodayAppointmentResponse> getUpcomingAppointments(
            LocalDateTime from,
            LocalDateTime to) {

        return appointmentRepository.findUpcomingAppointments(
                from.toLocalDate(),
                to.toLocalDate()
        );
    }

    private TodayAppointmentResponse mapToResponse(Appointment appointment) {
        return new TodayAppointmentResponse(
                appointment.getAppointmentId(),
                appointment.getDoctor().getDoctorName(),
                appointment.getPatientName(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus()
        );
    }
}