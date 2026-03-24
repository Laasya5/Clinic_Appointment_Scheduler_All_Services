package com.example.appointment_service.flowable;

import com.example.appointment_service.event.AppointmentEvent;
import com.example.appointment_service.event.AppointmentEventPublisher;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component("publishCancellationDelegate")
public class PublishCancellationDelegate implements JavaDelegate {

    private final AppointmentEventPublisher publisher;

    public PublishCancellationDelegate(AppointmentEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void execute(DelegateExecution execution) {

        publisher.publish(new AppointmentEvent(
                "CANCELLED",
                (String) execution.getVariable("patientName"),
                (String) execution.getVariable("doctorName"),
                (LocalDate) execution.getVariable("appointmentDate"),
                (LocalTime) execution.getVariable("appointmentTime")
        ));
    }
}
