package com.masai.scheduler.web;

import com.masai.scheduler.payload.User;
import com.masai.scheduler.quartz.job.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class UserCONTROLLER {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/find")
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/reminders")
    public ResponseEntity<String> setReminder(@RequestParam String email, @RequestParam String message, @RequestParam LocalDateTime time) {
        return ResponseEntity.ok(userService.setReminder(email, message, time));
    }


}
