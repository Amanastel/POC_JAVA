package com.flagsmith.flagsmith.controller;

import com.flagsmith.flagsmith.model.Reminder;
import com.flagsmith.flagsmith.model.User;
import com.flagsmith.flagsmith.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

//    @GetMapping
//    public List<Reminder> getRemindersByTimeAndUser(@RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
//                                                   @RequestBody User user) {
//        return reminderService.getRemindersByTimeAndUser(time, user);
//    }

//    @PostMapping
//    public void createReminder(@RequestParam Long userId, @RequestParam String message,
//                               @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
//        reminderService.scheduleReminderForUser(userId, message, time);
//    }
    @PostMapping
    public void createReminder(@RequestParam Long userId, @RequestParam String message,
                               @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time) {
        reminderService.createReminder(userId, message, time);
    }


}