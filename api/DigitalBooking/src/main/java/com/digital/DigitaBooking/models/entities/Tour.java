package com.digital.DigitaBooking.models.entities;

import com.digital.DigitaBooking.models.entities.score.Counter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String tourName;

    @Column(columnDefinition = "VARCHAR(1000)")
    @NotNull
    private String tourDescription;

    @Column
    @NotNull
    private String tourClassification;

    @Column
    @NotNull
    private Integer tourCapacity;

    @Column
    @NotNull
    private Boolean tourAvailability;

    @Column
    @NotNull
    private Double tourPrice;

    @Column
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time earliestCheckInHour;

    @Column
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time latestCheckInHour;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "tour_feature",
            joinColumns = @JoinColumn(name = "id_tour"),
            inverseJoinColumns =
            @JoinColumn(name = "id_feature")
    )
    private Set<Feature> features = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", referencedColumnName = "id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Image> images = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @JsonIgnore
    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToOne(mappedBy = "tour", cascade = CascadeType.ALL)
    private Counter counter;

    public void initializeCounter() {
        Counter justCreated = new Counter();
        justCreated.setTour(this);
        this.setCounter(justCreated);
    }

    public void addReservation(Reservation reservation) {

        reservations.add(reservation);
    }

    public void updateCategory(Category category) {
        this.getCategory().getTours().remove(this);
        this.setCategory(null);

        this.setCategory(category);
        category.getTours().add(this);
    }

    public void updateCountry(Country country) {
        this.getCountry().getTours().remove(this);
        this.setCountry(null);

        country.getTours().add(this);
        this.setCountry(country);
    }

}
