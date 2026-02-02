package com.example.remainder_service.Controller;

import com.example.remainder_service.scheduler.RemainderScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/remainders")
public class RemainderController {
    @Autowired
    private RemainderScheduler remainderScheduler;

    @GetMapping("/run")
    public String runned(){
        remainderScheduler.sendRemainders();
    return "Remainder job executed";
    }



}
