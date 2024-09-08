package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateUserRequest;
import com.scaler.bms.dtos.CreateUserResponse;
import com.scaler.bms.models.User;
import com.scaler.bms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bms/user")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("{} is initialized", this.getClass().getName());
    }

    @PostMapping()
    public @ResponseBody CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
        logger.info("Request received :: {}", request);
        User savedUser = null;
        savedUser = userService.createUser(request.getEmail());
        logger.info("User created :: {}", savedUser.getEmail());
        CreateUserResponse response = new CreateUserResponse();
        response.setUser(savedUser);
        return response;
    }
}