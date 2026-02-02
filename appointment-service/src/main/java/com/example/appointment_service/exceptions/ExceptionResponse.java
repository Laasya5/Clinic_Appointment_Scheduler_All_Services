package com.example.appointment_service.exceptions;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String response;
    private LocalDateTime time;
    private int status;
}
