package com.scaler.bms.controllers;

import com.scaler.bms.dtos.BookTicketRequest;
import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.Ticket;
import com.scaler.bms.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bms")
public class TicketController {
    private final TicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
        logger.info("{} is initialized", this.getClass().getName());
    }

    @PostMapping("/ticket")
    public Ticket bookTicket(@RequestBody BookTicketRequest request) throws EntityNotFoundException {
        logger.info("Attempting to book ticket for showId: {}, userId: {}, showSeatIds: {}", request.getShowId(), request.getUserId(), request.getShowSeatIds());
        Ticket ticket = this.ticketService.bookTicket(request.getShowId(), request.getShowSeatIds(), request.getUserId());
        logger.info("Ticket booked successfully for showId: {}, userId: {}", request.getShowId(), request.getUserId());
        return ticket;
    }
}