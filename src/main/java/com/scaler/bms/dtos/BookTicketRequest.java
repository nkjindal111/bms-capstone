package com.scaler.bms.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookTicketRequest {
    private Long showId;
    private List<Long> showSeatIds;
    private Long userId;
}