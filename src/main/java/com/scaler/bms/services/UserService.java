package com.scaler.bms.services;

import com.scaler.bms.models.User;
import com.scaler.bms.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    public User createUser(String email) {
        logger.info("Creating user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(existingUser -> {
                    logger.error("User already exists with email: {}. Skipping creating new user", email);
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    User savedUser = userRepository.save(newUser);
                    logger.info("User created successfully with id: {} and email: {}", savedUser.getId(), savedUser.getEmail());
                    return savedUser;
                });
    }
}