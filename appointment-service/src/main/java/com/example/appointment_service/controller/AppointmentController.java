package com.example.appointment_service.controller;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.entity.Appointment;
import com.example.appointment_service.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping("/create")
    public Appointment book(@RequestBody AppointmentDetails details) {
        return service.bookAppointment(details);
    }

    @GetMapping("/search/{name}")
    public Appointment getByPatient(@PathVariable String name) {
        return service.getByPatientName(name);
    }

    @DeleteMapping("/cancel/{name}")
    public Appointment cancel(@PathVariable String name) {
        return service.cancelByPatientName(name);
    }

    @GetMapping("/today")
    public List<TodayAppointmentResponse> today() {
        return service.getTodayAppointments();
    }

    @GetMapping("/booked")
    public List<TodayAppointmentResponse> booked() {
        return service.getBookedAppointments();
    }

    @GetMapping("/booked/date")
    public List<TodayAppointmentResponse> bookedByDate(@RequestParam LocalDate date) {
        return service.getBookedAppointmentsByDate(date);
    }
    @GetMapping("/upcoming")
    public List<TodayAppointmentResponse> getUpcomingAppointments(
            @RequestParam int hours) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime until = now.plusHours(hours);

        return service.getUpcomingAppointments(now, until);
    }
//    @DeleteMapping("/appointments/expired")
//    void deleteExpiredAppointments();


}
