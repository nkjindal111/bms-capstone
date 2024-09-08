package com.scaler.bms.services;

import com.scaler.bms.exceptions.EntityNotFoundException;
import com.scaler.bms.exceptions.EntityType;
import com.scaler.bms.models.*;
import com.scaler.bms.repositories.AudiRepository;
import com.scaler.bms.repositories.CityRepository;
import com.scaler.bms.repositories.SeatRepository;
import com.scaler.bms.repositories.TheatreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final CityRepository cityRepository;
    private final AudiRepository audiRepository;
    private final SeatRepository seatRepository;
    private static final Logger logger = LoggerFactory.getLogger(TheatreService.class);

    @Autowired
    public TheatreService(TheatreRepository theatreRepository,
                          CityRepository cityRepository,
                          AudiRepository audiRepository,
                          SeatRepository seatRepository) {
        this.theatreRepository = theatreRepository;
        this.cityRepository = cityRepository;
        this.audiRepository = audiRepository;
        this.seatRepository = seatRepository;
        logger.info("{} is initialized", this.getClass().getName());
    }

    public Theatre createTheatre(String name, String address, Long cityId) throws EntityNotFoundException {
        logger.info("Creating theatre with name: {}, address: {}, cityId: {}", name, address, cityId);
        City city = getCityById(cityId);
        Theatre theatre = saveTheatre(name, address);
        addTheatreToCity(city, theatre);
        return theatre;
    }

    private City getCityById(Long cityId) throws EntityNotFoundException {
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            logger.error("City with id {} not found", cityId);
            throw new EntityNotFoundException(EntityType.CITY, "City with id " + cityId + " not found");
        }
        return cityOptional.get();
    }

    private Theatre saveTheatre(String name, String address) {
        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);
        Theatre savedTheatre = theatreRepository.save(theatre);
        logger.info("Theatre created with id: {}", savedTheatre.getId());
        return savedTheatre;
    }

    private void addTheatreToCity(City city, Theatre theatre) {
        if (city.getTheatres() == null) {
            city.setTheatres(new ArrayList<>());
        }
        city.getTheatres().add(theatre);
        cityRepository.save(city);
        logger.info("Theatre with id {} added to city with id {}", theatre.getId(), city.getId());
    }

    public Theatre addAudi(Long theatreId, String name, int capacity) throws EntityNotFoundException {
        logger.info("Adding Audi with name: {}, capacity: {} to theatreId: {}", name, capacity, theatreId);
        Theatre theatre = getTheatreById(theatreId);
        Audi savedAudi = saveAudi(name, capacity, theatre);
        addAudiToTheatre(theatre, savedAudi);
        return theatre;
    }

    private Theatre getTheatreById(Long theatreId) throws EntityNotFoundException {
        return theatreRepository.findById(theatreId)
                .orElseThrow(() -> {
                    logger.error("Theatre with id {} not found", theatreId);
                    return new EntityNotFoundException(EntityType.THEATER, "Theatre with id " + theatreId + " not found");
                });
    }

    private Audi saveAudi(String name, int capacity, Theatre theatre) {
        Audi audi = new Audi();
        audi.setName(name);
        audi.setCapacity(capacity);
        audi.setTheatre(theatre);
        Audi savedAudi = audiRepository.save(audi);
        logger.info("Audi created with id: {}", savedAudi.getId());
        return savedAudi;
    }

    private void addAudiToTheatre(Theatre theatre, Audi savedAudi) {
        if (theatre.getAudis() == null) {
            theatre.setAudis(new ArrayList<>());
        }
        theatre.getAudis().add(savedAudi);
        theatreRepository.save(theatre);
        logger.info("Audi with id {} added to theatre with id {}", savedAudi.getId(), theatre.getId());
    }

    public void addSeats(Long audiId, Map<SeatType, Integer> seatCount) throws EntityNotFoundException {
        logger.info("Adding seats to audiId: {}, seatCount: {}", audiId, seatCount);
        Audi audi = getAudiById(audiId);
        List<Seat> seats = createSeats(seatCount);
        List<Seat> savedSeats = seatRepository.saveAll(seats);
        logger.info("Seats saved: {}", savedSeats);
        audi.setSeats(savedSeats);
        audiRepository.save(audi);
        logger.info("Seats added successfully to audiId: {}", audiId);
    }

    private Audi getAudiById(Long audiId) throws EntityNotFoundException {
        return audiRepository.findById(audiId)
                .orElseThrow(() -> {
                    logger.error("Audi with id {} not found", audiId);
                    return new EntityNotFoundException(EntityType.AUDI, "Audi with id " + audiId + " not found");
                });
    }

    private List<Seat> createSeats(Map<SeatType, Integer> seatCount) {
        List<Seat> seats = new ArrayList<>();
        for (Map.Entry<SeatType, Integer> entry : seatCount.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                Seat seat = new Seat();
                seat.setSeatType(entry.getKey());
                seat.setSeatNumber(entry.getKey().toString() + (i + 1));
                seats.add(seat);
            }
        }
        return seats;
    }
}