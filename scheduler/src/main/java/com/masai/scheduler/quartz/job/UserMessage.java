package com.masai.scheduler.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class UserMessage extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String subject = jobDataMap.getString("id");
        String email = jobDataMap.getString("email");
        String message = jobDataMap.getString("message");
        sendEmail(email, message);

//        sendEmail(mailProperties.getUsername(), email, subject, body);
    }


    private void sendEmail(String message, String email) {
        System.out.println("AMAN IS CALLING IS OWN FEATURE -----------------------");
        System.out.println("Email: " + email);
        System.out.println("Message: " + message);

    }
}
