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
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            logger.error("User already exists with email: {}. skipping creating new user", email);
            return optionalUser.get();
        }
        User user = new User();
        user.setEmail(email);
        User savedUser = userRepository.save(user);
        logger.info("User created successfully with id: {} and email: {}", savedUser.getId(), savedUser.getEmail());
        return savedUser;
    }
}