package com.example.remainder_service.FeignClient;


import com.example.remainder_service.DTO.AppointmentReminderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "appointment-service")
public interface AppointmentFeignClient {

    @GetMapping("/api/v1/appointments/upcoming")
    List<AppointmentReminderDTO> getUpcomingAppointments(
            @RequestParam int hours
    );

//    @DeleteMapping("/appointments/expired")
//    void deleteExpiredAppointments();

}