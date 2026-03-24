package com.example.appointment_service.controller;

import com.example.appointment_service.dto.*;
import com.example.appointment_service.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.flowable.engine.RuntimeService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

/**
 * REST Controller for managing appointment operations.
 *
 * <p>
 * Provides endpoints to create, search, cancel,
 * and retrieve appointments.
 * </p>
 *
 * <p>
 * Base URL: <b>/api/appointment-service/appointments</b>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/appointment-service/appointments")
public class AppointmentController {

    private final AppointmentService service;
    private final RuntimeService runtimeService;

    public AppointmentController(AppointmentService service,
                                 RuntimeService runtimeService) {
        this.service = service;
        this.runtimeService = runtimeService;
    }

    /**
     * Creates a new appointment.
     *
     * @param details appointment details
     * @return response containing booked appointment information
     */

    @PostMapping("/create")
    public TodayAppointmentResponse book(@RequestBody AppointmentDetails details) {
        return service.bookAppointment(details);
    }

    /**
     * Retrieves appointment details by patient name.
     *
     * @param name patient name
     * @return appointment details
     */
    @GetMapping("/search/{name}")
    public TodayAppointmentResponse getByPatient(@PathVariable String name) {
        return service.getByPatientName(name);
    }

    /**
     * Cancels an appointment by patient name.
     *
     * @param id patient id
     * @return updated appointment status
     */
    @DeleteMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {

        runtimeService.startProcessInstanceByKey(
                "cancelAppointmentProcess",
                Map.of("appointmentId", id)
        );
        System.out.println("Starting process for ID: " + id);
        return "Cancellation process started";

    }
    /**
     * Retrieves today's appointments.
     *
     * @return list of today's appointments
     */
    @GetMapping("/today")
    public List<TodayAppointmentResponse> today() {
        return service.getTodayAppointments();
    }

    /**
     * Retrieves all currently booked appointments.
     *
     * @return list of booked appointments
     */
    @GetMapping("/booked")
    public List<TodayAppointmentResponse> booked() {
        return service.getBookedAppointments();
    }

    /**
     * Retrieves booked appointments by date.
     *
     * @param date appointment date
     * @return list of booked appointments for the given date
     */
    @GetMapping("/booked/date")
    public List<TodayAppointmentResponse> bookedByDate(@RequestParam LocalDate date) {
        return service.getBookedAppointmentsByDate(date);
    }

    /**
     * Retrieves upcoming appointments within a given number of hours.
     *
     * @param hours number of hours from current time
     * @return list of upcoming appointments
     */
    @GetMapping("/upcoming")
    public List<TodayAppointmentResponse> getUpcomingAppointments(
            @RequestParam int hours) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime until = now.plusHours(hours);

        return service.getUpcomingAppointments(now, until);
    }


}
