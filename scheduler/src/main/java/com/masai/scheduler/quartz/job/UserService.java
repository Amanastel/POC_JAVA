package com.masai.scheduler.quartz.job;

import com.masai.scheduler.payload.EmailRequest;
import com.masai.scheduler.payload.User;
import com.masai.scheduler.repository.UserRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Scheduler scheduler;


    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String setReminder(String email, String message, LocalDateTime time) {
        User user = userRepository.findByEmail(email);
        User save = userRepository.save(user);
        JobDetail jobDetail = buildJobDetail(save, message, time);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZonedDateTime.now().getZone());
        Trigger trigger = buildTrigger(jobDetail, zonedDateTime);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return "Error scheduling reminder";
        }
        return "Reminder set successfully";
    }

    private JobDetail buildJobDetail(User user, String message, LocalDateTime time) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id", ""+user.getId());
        jobDataMap.put("email", user.getEmail());
        jobDataMap.put("message", message);
        return JobBuilder.newJob(UserMessage.class)
                .withIdentity(UUID.randomUUID().toString(), "user-jobs")
                .withDescription("Send message job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "user-triggers")
                .withDescription("send message trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
