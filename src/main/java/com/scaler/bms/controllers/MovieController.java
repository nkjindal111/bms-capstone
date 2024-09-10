package com.scaler.bms.controllers;

import com.scaler.bms.dtos.CreateMovieRequest;
import com.scaler.bms.models.Movie;
import com.scaler.bms.services.MovieService;
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
public class MovieController {
    private final MovieService movieService;
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
        logger.info("{} is initialized", this.getClass().getName());
    }
    @PostMapping("/movie")
    public @ResponseBody Movie addMovie(@RequestBody CreateMovieRequest request) {
        logger.info("Request received :: {}", request);
        Movie movie = this.movieService.addMovie(request.getName(), request.getLanguages(),request.getActors(),request.getLength(),request.getRating(),request.getMovieFeatures());
        logger.info("Movie added :: {}", request.getName());
        return movie;
    }

}