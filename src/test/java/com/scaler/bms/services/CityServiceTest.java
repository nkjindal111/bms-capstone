package com.scaler.bms.services;

import com.scaler.bms.models.City;
import com.scaler.bms.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCity_Success() {
        String cityName = "Test City";
        City city = new City();
        city.setName(cityName);
        when(cityRepository.save(any(City.class))).thenAnswer(i -> i.getArguments()[0]);

        City savedCity = cityService.addCity(cityName);

        assertNotNull(savedCity);
        assertEquals(cityName, savedCity.getName());
        verify(cityRepository, times(1)).save(any(City.class));
    }
}