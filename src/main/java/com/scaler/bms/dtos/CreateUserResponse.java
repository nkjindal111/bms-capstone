package com.scaler.bms.dtos;

import com.scaler.bms.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {
    private User user;
}