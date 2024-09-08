package com.scaler.bms.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddAudiRequest {
    private String name;
    private int capacity;
}