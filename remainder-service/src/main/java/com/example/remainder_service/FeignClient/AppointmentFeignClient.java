package com.example.remainder_service.feignclient;

import com.example.remainder_service.dto.AppointmentReminderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Feign client for communicating with the Appointment Service.
 *
 * <p>
 * This interface enables inter-service communication
 * between the Reminder Service and the Appointment Service.
 * </p>
 *
 * <p>
 * It retrieves upcoming appointments within a specified
 * time range to generate reminders.
 * </p>
 *
 * @since 1.0
 */
@FeignClient(name = "appointment-service")
public interface AppointmentFeignClient {

    /**
     * Retrieves upcoming appointments within the given number of hours.
     *
     * <p>
     * Internally calls:
     * <b>/api/appointment-service/appointments/upcoming</b>
     * </p>
     *
     * @param hours number of hours from current time
     * @return list of upcoming appointment details
     */
    @GetMapping("/api/appointment-service/appointments/upcoming")
    List<AppointmentReminderDTO> getUpcomingAppointments(
            @RequestParam int hours
    );
}
