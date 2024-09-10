package com.scaler.bms.dtos;

import com.scaler.bms.models.Language;
import com.scaler.bms.models.SeatType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;
@Getter
@Setter
@ToString
public class CreateShowRequest {
    private Date startTime;
    private Date endTime;
    private Long audiId;
    private Map<SeatType, Integer> seatPricing;
    private Language language;
}