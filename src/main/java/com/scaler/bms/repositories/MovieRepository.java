package com.scaler.bms.repositories;

import com.scaler.bms.models.City;
import com.scaler.bms.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Override
    Movie save(Movie movie);
    @Override
    Optional<Movie> findById(Long id);
}