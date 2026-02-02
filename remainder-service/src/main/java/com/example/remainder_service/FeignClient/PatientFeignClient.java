package com.example.remainder_service.FeignClient;

import com.example.remainder_service.DTO.PatientContactDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "patient-service")
public interface PatientFeignClient {

    @GetMapping("/api/v1/patients/name/{name}")
    List<PatientContactDTO> getPatientByName(
            @PathVariable String name
    );
}
