package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.*;
import com.scaler.bms.repositories.AudiRepository;
import com.scaler.bms.repositories.CityRepository;
import com.scaler.bms.repositories.SeatRepository;
import com.scaler.bms.repositories.TheatreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceTest {

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AudiRepository audiRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private TheatreService theatreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTheatre_Success() throws EntityNotFoundException {
        String name = "Test Theatre";
        String address = "123 Test St";
        Long cityId = 1L;
        City city = new City();
        city.setId(cityId);
        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(theatreRepository.save(any(Theatre.class))).thenAnswer(i -> i.getArguments()[0]);

        Theatre createdTheatre = theatreService.createTheatre(name, address, cityId);

        assertNotNull(createdTheatre);
        assertEquals(name, createdTheatre.getName());
        assertEquals(address, createdTheatre.getAddress());
        verify(cityRepository, times(1)).findById(cityId);
        verify(theatreRepository, times(1)).save(any(Theatre.class));
    }

    @Test
    void testCreateTheatre_CityNotFound() {
        String name = "Test Theatre";
        String address = "123 Test St";
        Long cityId = 1L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            theatreService.createTheatre(name, address, cityId);
        });
        assertEquals("City with id 1 not found", exception.getMessage());
        verify(cityRepository, times(1)).findById(cityId);
        verify(theatreRepository, never()).save(any(Theatre.class));
    }

    @Test
    void testAddAudi_Success() throws EntityNotFoundException {
        Long theatreId = 1L;
        String name = "Audi 1";
        int capacity = 100;
        Theatre theatre = new Theatre();
        theatre.setId(theatreId);
        when(theatreRepository.findById(theatreId)).thenReturn(Optional.of(theatre));
        when(audiRepository.save(any(Audi.class))).thenAnswer(i -> i.getArguments()[0]);
        when(theatreRepository.save(any(Theatre.class))).thenAnswer(i -> i.getArguments()[0]);

        Theatre updatedTheatre = theatreService.addAudi(theatreId, name, capacity);

        assertNotNull(updatedTheatre);
        assertEquals(1, updatedTheatre.getAudis().size());
        verify(theatreRepository, times(1)).findById(theatreId);
        verify(audiRepository, times(1)).save(any(Audi.class));
    }

    @Test
    void testAddAudi_TheatreNotFound() {
        Long theatreId = 1L;
        String name = "Audi 1";
        int capacity = 100;
        when(theatreRepository.findById(theatreId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            theatreService.addAudi(theatreId, name, capacity);
        });
        assertEquals("Theatre with id 1 not found", exception.getMessage());
        verify(theatreRepository, times(1)).findById(theatreId);
        verify(audiRepository, never()).save(any(Audi.class));
    }

    @Test
    void testAddSeats_Success() throws EntityNotFoundException {
        Long audiId = 1L;
        Map<SeatType, Integer> seatCount = new HashMap<>();
        seatCount.put(SeatType.REGULAR, 10);
        Audi audi = new Audi();
        audi.setId(audiId);
        when(audiRepository.findById(audiId)).thenReturn(Optional.of(audi));
        when(seatRepository.saveAll(anyList())).thenAnswer(i -> i.getArguments()[0]);

        theatreService.addSeats(audiId, seatCount);

        verify(audiRepository, times(1)).findById(audiId);
        verify(seatRepository, times(1)).saveAll(anyList());
        verify(audiRepository, times(1)).save(any(Audi.class));
    }

    @Test
    void testAddSeats_AudiNotFound() {
        Long audiId = 1L;
        Map<SeatType, Integer> seatCount = new HashMap<>();
        seatCount.put(SeatType.REGULAR, 10);
        when(audiRepository.findById(audiId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            theatreService.addSeats(audiId, seatCount);
        });
        assertEquals("Audi with id 1 not found", exception.getMessage());
        verify(audiRepository, times(1)).findById(audiId);
        verify(seatRepository, never()).saveAll(anyList());
    }
}