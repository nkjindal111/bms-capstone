package com.scaler.bms.services;

import com.scaler.bms.models.Actor;
import com.scaler.bms.models.Language;
import com.scaler.bms.models.Movie;
import com.scaler.bms.models.MovieFeature;
import com.scaler.bms.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMovie() {
        // Arrange
        String name = "Test Movie";
        List<Language> languages = List.of(Language.HINDI);
        Actor actor = new Actor();
        actor.setName("Test Actor");
        List<Actor> actors = List.of(actor);
        int length = 120;
        double rating = 8.5;
        List<MovieFeature> movieFeatures = List.of(MovieFeature.TWO_D);

        Movie movie = new Movie();
        movie.setName(name);
        movie.setLanguages(languages);
        movie.setActors(actors);
        movie.setLength(length);
        movie.setRating(rating);
        movie.setMovieFeatures(movieFeatures);

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        // Act
        Movie result = movieService.addMovie(name, languages, actors, length, rating, movieFeatures);

        // Assert
        assertEquals(name, result.getName());
        assertEquals(languages, result.getLanguages());
        assertEquals(actors, result.getActors());
        assertEquals(length, result.getLength());
        assertEquals(rating, result.getRating());
        assertEquals(movieFeatures, result.getMovieFeatures());
    }
}