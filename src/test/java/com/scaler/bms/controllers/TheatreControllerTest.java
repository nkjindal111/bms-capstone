// src/test/java/com/scaler/bms/controllers/TheatreControllerTest.java
package com.scaler.bms.controllers;

import com.scaler.bms.dtos.AddAudiRequest;
import com.scaler.bms.dtos.CreateTheaterRequest;
import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.SeatType;
import com.scaler.bms.models.Theatre;
import com.scaler.bms.services.TheatreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TheatreControllerTest {

    @Mock
    private TheatreService theatreService;

    @InjectMocks
    private TheatreController theatreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTheatre_ShouldCreateTheatre() throws EntityNotFoundException {
        Long cityId = 1L;
        CreateTheaterRequest request = new CreateTheaterRequest();
        request.setName("Test Theatre");
        request.setAddress("123 Test St");

        Theatre mockTheatre = new Theatre();
        mockTheatre.setName("Test Theatre");
        mockTheatre.setAddress("123 Test St");

        when(theatreService.createTheatre("Test Theatre", "123 Test St", cityId)).thenReturn(mockTheatre);

        Theatre result = theatreController.createTheatre(cityId, request);

        assertNotNull(result);
        assertEquals("Test Theatre", result.getName());
        assertEquals("123 Test St", result.getAddress());
        verify(theatreService, times(1)).createTheatre("Test Theatre", "123 Test St", cityId);
    }

    @Test
    void addAudi_ShouldAddAudi() throws EntityNotFoundException {
        Long theatreId = 1L;
        AddAudiRequest request = new AddAudiRequest();
        request.setName("Audi 1");
        request.setCapacity(100);

        Theatre mockTheatre = new Theatre();
        when(theatreService.addAudi(theatreId, "Audi 1", 100)).thenReturn(mockTheatre);

        Theatre result = theatreController.addAudi(theatreId, request);

        assertNotNull(result);
        verify(theatreService, times(1)).addAudi(theatreId, "Audi 1", 100);
    }

    @Test
    void addSeats_ShouldAddSeats() throws EntityNotFoundException {
        Long audiId = 1L;
        Map<SeatType, Integer> seatCount = new HashMap<>();
        seatCount.put(SeatType.REGULAR, 10);

        doNothing().when(theatreService).addSeats(audiId, seatCount);

        theatreController.addSeats(audiId, seatCount);

        verify(theatreService, times(1)).addSeats(audiId, seatCount);
    }
}