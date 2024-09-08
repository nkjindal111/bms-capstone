package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateCityRequest;
import com.scaler.bms.models.City;
import com.scaler.bms.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bms")
public class CityController {
    private final CityService cityService;
    private static final Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
        logger.info("{} is initialized", this.getClass().getName());
    }
    @PostMapping("/city")
    public @ResponseBody City addCity(@RequestBody CreateCityRequest request) {
        logger.info("Request received :: {}", request);
        City city = this.cityService.addCity(request.getName());
        logger.info("City added :: {}", request.getName());
        return city;
    }

}