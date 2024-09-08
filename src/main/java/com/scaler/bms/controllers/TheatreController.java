package com.scaler.bms.controllers;

import com.scaler.bms.dtos.AddAudiRequest;
import com.scaler.bms.dtos.CreateTheaterRequest;
import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.SeatType;
import com.scaler.bms.models.Theatre;
import com.scaler.bms.services.TheatreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/bms")
public class TheatreController {
    private final TheatreService theatreService;
    private static final Logger logger = LoggerFactory.getLogger(TheatreController.class);

    @Autowired
    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
        logger.info("{} is initialized", this.getClass().getName());
    }

    @PostMapping("city/{cityId}/theater")
    public @ResponseBody Theatre createTheatre(@PathVariable("cityId") Long cityId, @RequestBody CreateTheaterRequest request) throws EntityNotFoundException {
        logger.info("Request received :: {} cityId:{}", request, cityId);
        Theatre theatre = this.theatreService.createTheatre(request.getName(), request.getAddress(), cityId);
        logger.info("Theatre created successfully with name: {}", theatre.getName());
        return theatre;
    }

    @PostMapping("/theater/{theatreId}/audi")
    public @ResponseBody Theatre addAudi(@PathVariable(name = "theatreId") Long theatreId, @RequestBody AddAudiRequest request) throws EntityNotFoundException {
        logger.info("Request received :: {} TheaterId: {}", request, theatreId);
        Theatre theatre = theatreService.addAudi(theatreId, request.getName(), request.getCapacity());
        logger.info("audi added successfully to theatreId: {}", theatreId);
        return theatre;
    }

    /**
     * Adds seats to an audi.
     *
     * @param audiId the ID of the audi
     * @param seatCount    the map of seat types and their counts
     */
    @PostMapping("/audi/{audiId}/seats")
    public void addSeats(@PathVariable(name = "audiId") Long audiId, @RequestBody Map<SeatType, Integer> seatCount) throws EntityNotFoundException {
        logger.info("Request received :: audiId: {}, seatCount: {}", audiId, seatCount);
        theatreService.addSeats(audiId, seatCount);
        logger.info("Seats added successfully to audiId: {}", audiId);
    }
}