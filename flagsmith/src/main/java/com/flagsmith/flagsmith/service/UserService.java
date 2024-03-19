package com.flagsmith.flagsmith.service;

import com.flagsmith.flagsmith.model.User;
import com.flagsmith.flagsmith.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(User user) {
        // Check if the user already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("User already exists with this email");
        }
        return userRepository.save(user);
    }
}