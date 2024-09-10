package com.scaler.bms.dtos;

import com.scaler.bms.models.Actor;
import com.scaler.bms.models.Language;
import com.scaler.bms.models.MovieFeature;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateMovieRequest {
    private String name;
    private List<Language> languages;
    private List<Actor> actors;
    private int length;
    private double rating;
    private List<MovieFeature> movieFeatures;
}