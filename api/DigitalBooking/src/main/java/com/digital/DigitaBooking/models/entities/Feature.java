package com.digital.DigitaBooking.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String featureName;

    @JsonIgnore
    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    private Set<Tour> tours = new HashSet<>();

    public void addTour(Tour tour) {
        this.tours.add(tour);
        tour.getFeatures().add(this);
    }

//    public void removeFeature(long featureId) {
//        Feature feature = this.features.stream().filter(t -> t.getId() == featureId).findFirst().orElse(null);
//        if (feature != null) {
//            this.features.remove(feature);
//            feature.getTours().remove(this);
//        }
//    }

}
