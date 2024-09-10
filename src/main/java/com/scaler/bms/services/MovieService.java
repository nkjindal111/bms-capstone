package com.scaler.bms.services;

import com.scaler.bms.models.Actor;
import com.scaler.bms.models.Language;
import com.scaler.bms.models.Movie;
import com.scaler.bms.models.MovieFeature;
import com.scaler.bms.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    public Movie addMovie(String name, List<Language> languages, List<Actor> actors, int length, double rating, List<MovieFeature> movieFeatures) {
        logger.info("Adding movie with name: {}", name);
        Movie movie = new Movie();
        movie.setName(name);
        movie.setLanguages(languages);
        movie.setActors(actors);
        movie.setLength(length);
        movie.setRating(rating);
        movie.setMovieFeatures(movieFeatures);
        Movie savedMovie = movieRepository.save(movie);
        logger.info("Movie added successfully with name: {}", name);
        return savedMovie;
    }
}