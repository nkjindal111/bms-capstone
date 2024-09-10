package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.*;
import com.scaler.bms.repositories.AudiRepository;
import com.scaler.bms.repositories.MovieRepository;
import com.scaler.bms.repositories.ShowRepository;
import com.scaler.bms.repositories.ShowSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowServiceTest {

    @Mock
    private AudiRepository audiRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private ShowSeatRepository showSeatRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ShowService showService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShow_Success() throws EntityNotFoundException {
        Long movieId = 1L;
        Long audiId = 1L;
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 7200000); // 2 hours later
        Map<SeatType, Integer> seatPricing = new HashMap<>();
        Language language = Language.ENGLISH;

        Movie movie = new Movie();
        movie.setId(movieId);

        Audi audi = new Audi();
        audi.setId(audiId);
        audi.setSeats(Collections.singletonList(new Seat()));

        Show show = new Show();
        show.setId(1L);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(audiRepository.findById(audiId)).thenReturn(Optional.of(audi));
        when(showRepository.save(any(Show.class))).thenReturn(show);
        when(showSeatRepository.save(any(ShowSeat.class))).thenReturn(new ShowSeat());

        Show createdShow = showService.createShow(movieId, startTime, endTime, audiId, seatPricing, language);

        assertNotNull(createdShow);
        assertEquals(1L, createdShow.getId());
        verify(movieRepository).findById(movieId);
        verify(audiRepository).findById(audiId);
        verify(showRepository, times(2)).save(any(Show.class));
        verify(showSeatRepository).save(any(ShowSeat.class));
    }

    @Test
    void testCreateShow_MovieNotFound() {
        Long movieId = 1L;
        Long audiId = 1L;
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 7200000); // 2 hours later
        Map<SeatType, Integer> seatPricing = new HashMap<>();
        Language language = Language.ENGLISH;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            showService.createShow(movieId, startTime, endTime, audiId, seatPricing, language);
        });

        assertEquals("Movie with id 1 not found", exception.getMessage());
        verify(movieRepository).findById(movieId);
        verify(audiRepository, never()).findById(audiId);
    }

    @Test
    void testCreateShow_AudiNotFound() {
        Long movieId = 1L;
        Long audiId = 1L;
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 7200000); // 2 hours later
        Map<SeatType, Integer> seatPricing = new HashMap<>();
        Language language = Language.ENGLISH;

        Movie movie = new Movie();
        movie.setId(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(audiRepository.findById(audiId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            showService.createShow(movieId, startTime, endTime, audiId, seatPricing, language);
        });

        assertEquals("Auditorium with id 1 not found", exception.getMessage());
        verify(movieRepository).findById(movieId);
        verify(audiRepository).findById(audiId);
    }
}