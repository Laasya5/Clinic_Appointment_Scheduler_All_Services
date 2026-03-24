package com.example.remainder_service.controller;

import com.example.remainder_service.scheduler.RemainderScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RemainderControllerTest {

    @Mock
    private RemainderScheduler remainderScheduler;

    @InjectMocks
    private RemainderController remainderController;

    @Test
    void testRunEndpoint() throws Exception {

        MockMvc mockMvc =
                MockMvcBuilders.standaloneSetup(remainderController).build();

        mockMvc.perform(get("/api/remainder-service/remainders/run"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reminder job executed"));

        verify(remainderScheduler, times(1)).sendRemainders();
    }
}
