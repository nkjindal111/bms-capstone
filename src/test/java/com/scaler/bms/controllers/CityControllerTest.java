package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateCityRequest;
import com.scaler.bms.models.City;
import com.scaler.bms.services.CityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCity_ShouldAddCity() {
        CreateCityRequest request = new CreateCityRequest();
        request.setName("Test City");

        City mockCity = new City();
        mockCity.setName("Test City");

        Mockito.when(cityService.addCity("Test City")).thenReturn(mockCity);

        City result = cityController.addCity(request);

        Assertions.assertEquals("Test City", result.getName());
        Mockito.verify(cityService, Mockito.times(1)).addCity("Test City");
    }
}