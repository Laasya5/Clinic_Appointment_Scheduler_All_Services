package com.example.remainder_service.feignclient;

import com.example.remainder_service.dto.PatientContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Feign client for communicating with the Patient Service.
 *
 * <p>
 * This interface enables the Reminder Service to fetch
 * patient contact details required for sending reminders.
 * </p>
 *
 * <p>
 * It calls the Patient Service REST endpoint and retrieves
 * minimal patient information (name, email, phone).
 * </p>
 *
 * @since 1.0
 */
@FeignClient(name = "patient-service")
public interface PatientFeignClient {

    /**
     * Retrieves patient contact details by patient name.
     *
     * <p>
     * Internally calls:
     * <b>/api/patient-service/patients/name/{name}</b>
     * </p>
     *
     * @param name patient name
     * @return list of matching patient contact details
     */
    @GetMapping("/api/patient-service/patients/name/{name}")
    List<PatientContactDTO> getPatientByName(
            @PathVariable String name
    );
}
