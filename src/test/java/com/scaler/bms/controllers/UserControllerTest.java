package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateUserRequest;
import com.scaler.bms.dtos.CreateUserResponse;
import com.scaler.bms.models.User;
import com.scaler.bms.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCreateUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        when(userService.createUser("test@example.com")).thenReturn(mockUser);

        CreateUserResponse response = userController.createUser(request);

        assertNotNull(response);
        assertNotNull(response.getUser());
        assertEquals("test@example.com", response.getUser().getEmail());
        verify(userService, times(1)).createUser("test@example.com");
    }
}