package com.example.remainder_service.controller;

import com.example.remainder_service.scheduler.RemainderScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for triggering reminder operations.
 *
 * <p>
 * Provides endpoints to manually trigger the reminder scheduler.
 * Normally reminders are executed automatically using
 * scheduled tasks.
 * </p>
 *
 * <p>
 * Base URL: <b>/api/remainder-service/remainders</b>
 * </p>
 *
 * @since 1.0
 */
@RestController
@RequestMapping("/api/remainder-service/remainders")
public class RemainderController {

    private final RemainderScheduler remainderScheduler;

    /**
     * Constructor-based dependency injection.
     *
     * @param remainderScheduler scheduler service
     */
    public RemainderController(RemainderScheduler remainderScheduler) {
        this.remainderScheduler = remainderScheduler;
    }

    /**
     * Manually triggers the reminder scheduler.
     *
     * <p>
     * Useful for testing or forcing reminder execution
     * without waiting for scheduled cron timing.
     * </p>
     *
     * @return confirmation message
     */
    @GetMapping("/run")
    public String runned() {
        remainderScheduler.sendRemainders();
        return "Reminder job executed";
    }
}
