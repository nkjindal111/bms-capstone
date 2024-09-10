package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateUserRequest;
import com.scaler.bms.models.User;
import com.scaler.bms.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@example.com");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        Mockito.when(userService.createUser("test@example.com")).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/bms/user")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test@example.com"));

        Mockito.verify(userService, Mockito.times(1)).createUser("test@example.com");
    }
}