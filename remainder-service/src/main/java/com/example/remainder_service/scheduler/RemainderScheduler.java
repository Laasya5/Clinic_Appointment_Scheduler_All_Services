package com.example.remainder_service.scheduler;

import com.example.remainder_service.DTO.AppointmentReminderDTO;
import com.example.remainder_service.DTO.PatientContactDTO;
import com.example.remainder_service.FeignClient.AppointmentFeignClient;
import com.example.remainder_service.FeignClient.PatientFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RemainderScheduler {

    private final AppointmentFeignClient appointmentClient;
    private final PatientFeignClient patientClient;

    // Runs every minute (easy to test, safe for production too)
    @Scheduled(cron = "0 * * * * *")
    public void sendRemainders() {

        System.out.println("Reminder Scheduler Started");

        try {

//            appointmentClient.deleteExpiredAppointments();
//            System.out.println("Expired appointments cleaned");
            List<AppointmentReminderDTO> appointments =
                    appointmentClient.getUpcomingAppointments(24);

            System.out.println("Appointments found: " + appointments.size());

            LocalDateTime now = LocalDateTime.now();

            for (AppointmentReminderDTO appt : appointments) {

                LocalDateTime appointmentDateTime =
                        LocalDateTime.of(
                                appt.getAppointmentDate(),
                                appt.getAppointmentTime()
                        );

                long minutesDiff =
                        Duration.between(now, appointmentDateTime).toMinutes();

                System.out.println("Minutes diff: " + minutesDiff);

                // 24-hour reminder window (±5 minutes)
                if (minutesDiff >= 1435 && minutesDiff <= 1445) {
                    sendReminder(appt, "24 HOURS REMINDER");
                }

                // 1-hour reminder window (±5 minutes)
                if (minutesDiff >= 55 && minutesDiff <= 65) {
                    sendReminder(appt, "1 HOUR REMINDER");
                }
            }

        } catch (Exception e) {
            System.err.println("Reminder Scheduler Failed");
            e.printStackTrace();
        }
    }

    private void sendReminder(AppointmentReminderDTO appt, String type) {

        List<PatientContactDTO> patients =
                patientClient.getPatientByName(appt.getPatientName());

        if (patients == null || patients.isEmpty()) {
            System.out.println("No patient found for: " + appt.getPatientName());
            return;
        }

        PatientContactDTO patient = patients.get(0); // take first match

        System.out.println(type);
        System.out.println("Patient: " + patient.getName());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("Phone: " + patient.getPhone());
        System.out.println("Doctor: " + appt.getDoctorName());
        System.out.println("Time: " + appt.getAppointmentDate()
                + " " + appt.getAppointmentTime());
        System.out.println("----------------------------------");
    }
}