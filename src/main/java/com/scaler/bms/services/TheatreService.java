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

    public Theatre createTheatre(
            String name,
            String address,
            Long cityId
    ) throws EntityNotFoundException {
        logger.info("Creating theatre with name: {}, address: {}, cityId: {}", name, address, cityId);
        Optional<City> cityOptional = cityRepository.findById(cityId);
        if (cityOptional.isEmpty()) {
            logger.error("City with id {} not found", cityId);
            throw new EntityNotFoundException(EntityType.CITY, "City with id " + cityId + " not found");
        }

        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setAddress(address);
        Theatre savedTheatre = theatreRepository.save(theatre);
        logger.info("Theatre created with id: {}", savedTheatre.getId());

        City dbCity = cityOptional.get();
        if (dbCity.getTheatres() == null) {
            dbCity.setTheatres(new ArrayList<>());
        }
        dbCity.getTheatres().add(savedTheatre);
        cityRepository.save(dbCity);
        logger.info("Theatre with id {} added to city with id {}", savedTheatre.getId(), cityId);

        return savedTheatre;
    }

    public Theatre addAudi(Long theatreId, String name, int capacity) throws EntityNotFoundException {
        logger.info("Adding Audi with name: {}, capacity: {} to theatreId: {}", name, capacity, theatreId);
        Theatre theatre = theatreRepository.findById(theatreId).orElse(null);
        if (theatre == null) {
            logger.error("Theatre with id {} not found", theatreId);
            throw new EntityNotFoundException(EntityType.THEATER, "Theatre with id " + theatreId + " not found");
        }

        Audi audi = new Audi();
        audi.setName(name);
        audi.setCapacity(capacity);
        audi.setTheatre(theatre);
        Audi savedAudi = audiRepository.save(audi);
        logger.info("Audi created with id: {}", savedAudi.getId());

        if (theatre.getAudis() == null) {
            theatre.setAudis(new ArrayList<>());
        }
        theatre.getAudis().add(savedAudi);
        Theatre updatedTheatre = theatreRepository.save(theatre);
        logger.info("Audi with id {} added to theatre with id {}", savedAudi.getId(), theatreId);

        return updatedTheatre;
    }

    public void addSeats(Long audiId, Map<SeatType, Integer> seatCount) throws EntityNotFoundException {
        logger.info("Adding seats to audiId: {}, seatCount: {}", audiId, seatCount);
        Audi audi = audiRepository.findById(audiId).orElse(null);
        if (audi == null) {
            logger.error("Audi with id {} not found", audiId);
            throw new EntityNotFoundException(EntityType.AUDI, "Audi with id " + audiId + " not found");
        }

        List<Seat> seats = new ArrayList<>();
        for (Map.Entry<SeatType, Integer> entry : seatCount.entrySet()) {
            for (int i = 0; i < entry.getValue(); ++i) {
                Seat seat = new Seat();
                seat.setSeatType(entry.getKey());
                seat.setSeatNumber(entry.getKey().toString() + (i + 1));
                seats.add(seat);
            }
        }

        List<Seat> savedSeats = seatRepository.saveAll(seats);
        logger.info("Seats saved: {}", savedSeats);

        audi.setSeats(savedSeats);
        audiRepository.save(audi);
        logger.info("Seats added successfully to audiId: {}", audiId);
    }
}