package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateUserRequest;
import com.scaler.bms.dtos.CreateUserResponse;
import com.scaler.bms.models.User;
import com.scaler.bms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("UserController initialized with UserService");
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        logger.info("Received request to create user with email: {}", request.getEmail());
        User savedUser = userService.createUser(request.getEmail());
        logger.info("User created successfully with email: {}", request.getEmail());

        CreateUserResponse response = new CreateUserResponse();
        response.setUser(savedUser);
        logger.info("Returning response with created user: {}", savedUser.getEmail());

        return response;
    }
}