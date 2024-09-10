package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateShowRequest;
import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.models.Show;
import com.scaler.bms.services.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bms")
public class ShowController {

    private final ShowService showService;
    private static final Logger logger = LoggerFactory.getLogger(ShowController.class);

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
        logger.info("{} is initialized", this.getClass().getName());
    }

    @PostMapping("/movie/{movieId}/show")
    public @ResponseBody Show createShow(@PathVariable("movieId") Long movieId, @RequestBody CreateShowRequest request) throws EntityNotFoundException {
        logger.info("Request Received :: {}, movieId:{}", request, movieId);
        Show show = showService.createShow(
                movieId, request.getStartTime(), request.getEndTime(), request.getAudiId(), request.getSeatPricing(), request.getLanguage());
        logger.info("Show created successfully with id: {}", show.getId());
        return show;
    }
}