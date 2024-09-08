package com.scaler.bms;

import com.scaler.bms.controllers.UserController;
import com.scaler.bms.dtos.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BMSApplication implements CommandLineRunner {

    private UserController userController;

    @Autowired
    public BMSApplication(UserController userController) {
        this.userController = userController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BMSApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*CreateUserRequest requestDto = new CreateUserRequest();
        requestDto.setEmail("nkjindal111@gmail.com");

        this.userController.createUser(requestDto);*/
    }
}
