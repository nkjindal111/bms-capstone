package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateCityRequest;
import com.scaler.bms.models.City;
import com.scaler.bms.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {CityController.class, UserController.class})
class CityControllerTest {

    @MockBean
    private CityService cityService;

    @MockBean
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCity_ShouldAddCity() throws Exception {
        CreateCityRequest request = new CreateCityRequest();
        request.setName("Test City");

        City mockCity = new City();
        mockCity.setName("Test City");

        Mockito.when(cityService.addCity("Test City")).thenReturn(mockCity);

        mockMvc.perform(MockMvcRequestBuilders.post("/bms/city")
                        .contentType("application/json")
                        .content("{\"name\":\"Test City\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test City"));

        Mockito.verify(cityService, Mockito.times(1)).addCity("Test City");
    }
}