package com.example.patient_service;

import org.junit.jupiter.api.Test;

class PatientServiceApplicationTest {

    @Test
    void main() {
        PatientServiceApplication.main(new String[] {"--spring.main.web-application-type=none"});
    }
}
