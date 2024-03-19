package com.flagsmith.flagsmith.service;

import com.flagsmith.flagsmith.model.Reminder;
import com.flagsmith.flagsmith.model.User;
import com.flagsmith.flagsmith.repo.ReminderRepository;
import com.flagsmith.flagsmith.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    public void scheduleReminder(Reminder reminder) {
        // Convert LocalDateTime to Cron expression
        String cronExpression = toCronExpression(reminder.getTime());

        // Create a CronTrigger with the cron expression
        Trigger trigger = new CronTrigger(cronExpression);

        // Schedule the task with the trigger
        taskScheduler.schedule(() -> sendReminder(reminder), trigger);
    }

    private String toCronExpression(LocalDateTime localDateTime) {
        return String.format("%d %d %d %d %d ?",
                localDateTime.getSecond(),
                localDateTime.getMinute(),
                localDateTime.getHour(),
                localDateTime.getDayOfMonth(),
                localDateTime.getMonthValue());
    }

    public List<Reminder> getRemindersByTimeAndUser(LocalDateTime time, User user) {
        return reminderRepository.findByUserAndTime(user, time);
    }

    private void sendReminder(Reminder reminder) {
        System.out.println("Reminder for: " + reminder.getUser().getEmail() + ", Message: " + reminder.getMessage());
    }

    public void createReminder(Long userID, String message, LocalDateTime time) {
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found"));
        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setMessage(message);
        reminder.setTime(time);
        Reminder saved = reminderRepository.save(reminder);
        scheduleReminder(saved);
    }




    /*
    public void scheduleReminderForUser(Long userId, String message, LocalDateTime reminderTime) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setMessage(message);
        reminder.setTime(reminderTime);
        reminderRepository.save(reminder);

        // Schedule the reminder
        scheduleReminder(reminder);
    }

    private void scheduleReminder(Reminder reminder) {
        LocalDateTime now = LocalDateTime.now();
        if (reminder.getTime().isAfter(now)) { // Ensure the reminder time is in the future
            Date startTime = Date.from(reminder.getTime().atZone(ZoneId.systemDefault()).toInstant());
            taskScheduler.schedule(() -> sendReminder(reminder), startTime);
        } else {
            throw new RuntimeException("Reminder time must be in the future");
        }
    }

    @Scheduled(fixedRate = 6000) // Check every minute
    public void sendScheduledReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> reminders = reminderRepository.findByTime(now);
        System.out.println("Checking for reminders at: " + now);

        for (Reminder reminder : reminders) {
            sendReminder(reminder);
        }
    }

    private void sendReminder(Reminder reminder) {
        System.out.println("Reminder for: " + reminder.getUser().getEmail() + ", Message: " + reminder.getMessage());
    }

    // Other methods...


    // Other methods...


     */

}