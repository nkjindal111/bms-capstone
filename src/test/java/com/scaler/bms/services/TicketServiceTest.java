package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.*;
import com.scaler.bms.repositories.ShowRepository;
import com.scaler.bms.repositories.ShowSeatRepository;
import com.scaler.bms.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookTicket_Success() throws EntityNotFoundException {
        Long showId = 1L;
        Long userId = 1L;
        List<Long> showSeatIds = Arrays.asList(1L, 2L);

        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setId(1L);
        showSeat1.setState(ShowSeatStatus.AVAILABLE);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setId(2L);
        showSeat2.setState(ShowSeatStatus.AVAILABLE);

        when(showSeatRepository.findByIdIn(showSeatIds)).thenReturn(Arrays.asList(showSeat1, showSeat2));
        when(showRepository.findById(showId)).thenReturn(Optional.of(new Show()));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket ticket = ticketService.bookTicket(showId, showSeatIds, userId);

        assertNotNull(ticket);
        assertEquals(TicketStatus.SUCCESS, ticket.getTicketStatus());
        assertEquals(2, ticket.getShowSeats().size());
        verify(showSeatRepository, times(4)).save(any(ShowSeat.class));
    }

    @Test
    void testBookTicket_SeatNotAvailable() {
        Long showId = 1L;
        Long userId = 1L;
        List<Long> showSeatIds = Arrays.asList(1L, 2L);

        ShowSeat showSeat1 = new ShowSeat();
        showSeat1.setId(1L);
        showSeat1.setState(ShowSeatStatus.AVAILABLE);

        ShowSeat showSeat2 = new ShowSeat();
        showSeat2.setId(2L);
        showSeat2.setState(ShowSeatStatus.BOOKED);

        when(showSeatRepository.findByIdIn(showSeatIds)).thenReturn(Arrays.asList(showSeat1, showSeat2));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            ticketService.bookTicket(showId, showSeatIds, userId);
        });

        assertEquals("Show seat is not available: 2", exception.getMessage());
        verify(showSeatRepository, never()).save(any(ShowSeat.class));
    }
}