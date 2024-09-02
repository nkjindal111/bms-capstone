package com.scaler.bms.services;

import com.scaler.bms.models.User;
import com.scaler.bms.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_SaveAndReturnUser() {
        // GIVEN
        String email = "test@example.com";
        User mockUser = new User();
        mockUser.setEmail(email);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // WHEN
        User result = userService.createUser(email);

        // THEN
        assertEquals(mockUser, result);
        assertEquals(email, result.getEmail());
    }
}