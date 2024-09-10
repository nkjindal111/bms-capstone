package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.exceptions.EntityType;
import com.scaler.bms.models.ShowSeat;
import com.scaler.bms.models.ShowSeatStatus;
import com.scaler.bms.models.Ticket;
import com.scaler.bms.models.TicketStatus;
import com.scaler.bms.repositories.ShowRepository;
import com.scaler.bms.repositories.ShowSeatRepository;
import com.scaler.bms.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    public TicketService(ShowSeatRepository showSeatRepository, ShowRepository showRepository, TicketRepository ticketRepository) {
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    // Main logic for booking
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(Long showId, List<Long> showSeatIds, Long userId) throws EntityNotFoundException {
        logger.info("Attempting to book ticket for showId: {}, userId: {}, showSeatIds: {}", showId, userId, showSeatIds);

        // Check availability and lock seats
        List<ShowSeat> showSeats = checkAvailabilityAndLock(showSeatIds);

        // Assuming payment confirmation done
        // Return the ticket object
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.SUCCESS);
        ticket.setShowSeats(showSeats);
        ticket.setShow(showRepository.findById(showId).orElse(null));
        ticket = ticketRepository.save(ticket);
        logger.info("Ticket created with id: {} for showId: {}", ticket.getId(), showId);

        // Update the status of showSeats to booked
        for (ShowSeat showSeat : showSeats) {
            showSeat.setState(ShowSeatStatus.BOOKED);
            showSeatRepository.save(showSeat);
        }
        logger.info("Show seats booked for ticket id: {}", ticket.getId());

        return ticket;
    }

    private List<ShowSeat> checkAvailabilityAndLock(List<Long> showSeatIds) throws EntityNotFoundException {
        logger.info("Checking availability and locking seats for showSeatIds: {}", showSeatIds);

        // Fetch the given showSeats
        List<ShowSeat> showSeats = showSeatRepository.findByIdIn(showSeatIds);

        // Check for availability
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getState().equals(ShowSeatStatus.AVAILABLE)) {
                logger.error("Show seat is not available: {}", showSeat.getId());
                throw new EntityNotFoundException(EntityType.SEAT,"Show seat is not available: " + showSeat.getId());
            }
        }

        // Update the status to lock
        for (ShowSeat showSeat : showSeats) {
            showSeat.setState(ShowSeatStatus.LOCKED);
            showSeatRepository.save(showSeat);
        }
        logger.info("Show seats locked for showSeatIds: {}", showSeatIds);

        return showSeats;
    }
}