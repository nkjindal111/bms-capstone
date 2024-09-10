package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.exceptions.EntityType;
import com.scaler.bms.models.*;
import com.scaler.bms.repositories.AudiRepository;
import com.scaler.bms.repositories.MovieRepository;
import com.scaler.bms.repositories.ShowRepository;
import com.scaler.bms.repositories.ShowSeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShowService {
    private final AudiRepository audiRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final MovieRepository movieRepository;
    private static final Logger logger = LoggerFactory.getLogger(ShowService.class);

    @Autowired
    public ShowService(AudiRepository audiRepository,
                       ShowRepository showRepository,
                       ShowSeatRepository showSeatRepository,
                       MovieRepository movieRepository) {
        this.audiRepository = audiRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.movieRepository = movieRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    public Show createShow(Long movieId, Date startTime, Date endTime, Long audiId, Map<SeatType, Integer> seatPricing, Language language) throws EntityNotFoundException {
        logger.info("Creating show for movieId: {}, audiId: {}, language: {}, startTime: {}, endTime: {}",
                movieId, audiId, language, startTime, endTime);

        Movie movie = getMovieById(movieId);
        Audi audi = getAudiById(audiId);

        Show show = buildShow(startTime, endTime, language, movie, audi);
        Show savedShow = showRepository.save(show);
        logger.info("Show created with id: {}", savedShow.getId());

        List<ShowSeat> savedShowSeats = createShowSeats(savedShow, audi);
        savedShow.setShowSeats(savedShowSeats);
        Show finalShow = showRepository.save(savedShow);
        logger.info("Show seats added to show with id: {}", finalShow.getId());

        return finalShow;
    }

    private Movie getMovieById(Long movieId) throws EntityNotFoundException {
        return movieRepository.findById(movieId).orElseThrow(() -> {
            logger.error("Movie with id {} not found", movieId);
            return new EntityNotFoundException(EntityType.MOVIE, "Movie with id " + movieId + " not found");
        });
    }

    private Audi getAudiById(Long audiId) throws EntityNotFoundException {
        return audiRepository.findById(audiId).orElseThrow(() -> {
            logger.error("Auditorium with id {} not found", audiId);
            return new EntityNotFoundException(EntityType.AUDI, "Auditorium with id " + audiId + " not found");
        });
    }

    private Show buildShow(Date startTime, Date endTime, Language language, Movie movie, Audi audi) {
        Show show = new Show();
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        show.setLanguage(language);
        show.setMovie(movie);
        show.setAudi(audi);
        return show;
    }

    private List<ShowSeat> createShowSeats(Show show, Audi audi) {
        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (Seat seat : audi.getSeats()) {
            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setState(ShowSeatStatus.AVAILABLE);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
        return savedShowSeats;
    }
}