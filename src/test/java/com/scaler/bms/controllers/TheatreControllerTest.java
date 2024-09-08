package com.scaler.bms.controllers;

import com.scaler.bms.dtos.AddAudiRequest;
import com.scaler.bms.dtos.CreateTheaterRequest;
import com.scaler.bms.models.SeatType;
import com.scaler.bms.models.Theatre;
import com.scaler.bms.services.TheatreService;
import com.scaler.bms.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
@WebMvcTest(controllers = {TheatreController.class, UserController.class})
class TheatreControllerTest {

    @MockBean
    private TheatreService theatreService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createTheatre_ShouldCreateTheatre() throws Exception {
        Long cityId = 1L;
        CreateTheaterRequest request = new CreateTheaterRequest();
        request.setName("Test Theatre");
        request.setAddress("123 Test St");

        Theatre mockTheatre = new Theatre();
        mockTheatre.setName("Test Theatre");
        mockTheatre.setAddress("123 Test St");

        when(theatreService.createTheatre("Test Theatre", "123 Test St", cityId)).thenReturn(mockTheatre);

        mockMvc.perform(MockMvcRequestBuilders.post("/bms/city/{cityId}/theater", cityId)
                        .contentType("application/json")
                        .content("{\"name\":\"Test Theatre\", \"address\":\"123 Test St\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Theatre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("123 Test St"));

        verify(theatreService, times(1)).createTheatre("Test Theatre", "123 Test St", cityId);
    }

    @Test
    void addAudi_ShouldAddAudi() throws Exception {
        Long theatreId = 1L;
        AddAudiRequest request = new AddAudiRequest();
        request.setName("Audi 1");
        request.setCapacity(100);

        Theatre mockTheatre = new Theatre();
        when(theatreService.addAudi(theatreId, "Audi 1", 100)).thenReturn(mockTheatre);

        mockMvc.perform(MockMvcRequestBuilders.post("/bms/theater/{theatreId}/audi", theatreId)
                        .contentType("application/json")
                        .content("{\"name\":\"Audi 1\", \"capacity\":100}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(theatreService, times(1)).addAudi(theatreId, "Audi 1", 100);
    }

    @Test
    void addSeats_ShouldAddSeats() throws Exception {
        Long audiId = 1L;
        Map<SeatType, Integer> seatCount = new HashMap<>();
        seatCount.put(SeatType.REGULAR, 10);

        doNothing().when(theatreService).addSeats(audiId, seatCount);

        mockMvc.perform(MockMvcRequestBuilders.post("/bms/audi/{audiId}/seats", audiId)
                        .contentType("application/json")
                        .content("{\"REGULAR\":10}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(theatreService, times(1)).addSeats(audiId, seatCount);
    }
}