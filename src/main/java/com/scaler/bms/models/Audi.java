package com.scaler.bms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Audi extends BaseModel {
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Seat> seats;
    private int capacity;

    @ElementCollection // M:M
    @Enumerated(EnumType.STRING)
    private List<AudiFeature> audiFeatures;

    @ManyToOne
    private Theatre theatre;
}