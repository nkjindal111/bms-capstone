package com.scaler.bms.services;

import com.scaler.bms.models.City;
import com.scaler.bms.repositories.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    public City addCity(String name) {
        logger.info("Adding city with name: {}", name);
        City newCity = new City();
        newCity.setName(name);
        City savedCity = cityRepository.save(newCity);
        logger.info("City added successfully with name: {}", name);
        return savedCity;
    }
}