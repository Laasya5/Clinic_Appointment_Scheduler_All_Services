package com.example.remainder_service.scheduler;

import com.example.remainder_service.dto.AppointmentReminderDTO;
import com.example.remainder_service.dto.PatientContactDTO;
import com.example.remainder_service.feignclient.AppointmentFeignClient;
import com.example.remainder_service.feignclient.PatientFeignClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduler service responsible for sending appointment reminders.
 *
 * <p>
 * This service:
 * </p>
 * <ul>
 *     <li>Fetches upcoming appointments from Appointment Service</li>
 *     <li>Fetches patient contact details from Patient Service</li>
 *     <li>Calculates time difference</li>
 *     <li>Triggers reminders for 24-hour and 1-hour intervals</li>
 * </ul>
 *
 * <p>
 * Runs automatically using Spring's {@link Scheduled} annotation.
 * </p>
 *
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RemainderScheduler {

    private static final Logger log =
            LoggerFactory.getLogger(RemainderScheduler.class);

    private final AppointmentFeignClient appointmentClient;
    private final PatientFeignClient patientClient;

    /**
     * Scheduled method that runs every minute.
     *
     * <p>
     * Cron Expression: <b>"0 * * * * *"</b>
     * </p>
     *
     * <p>
     * Fetches upcoming appointments within the next 24 hours
     * and sends reminders when:
     * </p>
     * <ul>
     *     <li>24 hours remaining</li>
     *     <li>1 hour remaining</li>
     * </ul>
     */
    @Scheduled(cron = "0 * * * * *")
    public void sendRemainders() {

        log.info("Reminder Scheduler Started");

        try {
            List<AppointmentReminderDTO> appointments =
                    appointmentClient.getUpcomingAppointments(24);

            log.info("Appointments found: {}", appointments.size());

            LocalDateTime now = LocalDateTime.now();
            log.debug("Current time: {}", now);

            for (AppointmentReminderDTO appt : appointments) {

                LocalDateTime appointmentDateTime = LocalDateTime.of(
                        appt.getAppointmentDate(),
                        appt.getAppointmentTime()
                );

                long minutesDiff =
                        Duration.between(now, appointmentDateTime).toMinutes();

                if (minutesDiff < 0) {
                    continue;
                }

                log.debug("Appointment Time: {}", appointmentDateTime);
                log.debug("Minutes remaining: {}", minutesDiff);

                if (minutesDiff >= 1435 && minutesDiff <= 1445) {
                    sendReminder(appt, "24 HOURS REMINDER");
                }

                if (minutesDiff >= 55 && minutesDiff <= 65) {
                    sendReminder(appt, "1 HOUR REMINDER");
                }
            }

        } catch (Exception e) {
            log.error("Reminder Scheduler Failed", e);
        }
    }

    /**
     * Sends reminder notification for a given appointment.
     *
     * <p>
     * Fetches patient contact details and logs reminder information.
     * </p>
     *
     * @param appt appointment details
     * @param type reminder type (24 HOURS / 1 HOUR)
     */
    private void sendReminder(AppointmentReminderDTO appt, String type) {

        List<PatientContactDTO> patients;

        try {
            patients = patientClient.getPatientByName(appt.getPatientName());
        } catch (feign.FeignException e) {
            log.error("Failed to fetch patient: {} | HTTP {}",
                    appt.getPatientName(), e.status());
            return;
        }

        if (patients == null || patients.isEmpty()) {
            log.warn("No patient found for: {}", appt.getPatientName());
            return;
        }

        PatientContactDTO patient = patients.get(0);

        log.info("{}", type);
        log.info("Patient     : {}", patient.getName());
        log.info("Email       : {}", patient.getEmail());
        log.info("Phone       : {}", patient.getPhone());
        log.info("Doctor      : {}", appt.getDoctorName());
        log.info("Appointment : {} {}",
                appt.getAppointmentDate(),
                appt.getAppointmentTime());
    }
}
