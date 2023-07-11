package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TourDTO {

    private Long id;
    private String tourName;
    private String tourDescription;
    private String tourClassification;
    private Integer tourCapacity;
    private Boolean tourAvailability;
    private Double tourPrice;
    private Integer categoryId;
    private Integer CountryId;
    private Set<Feature> features;
    private Set<Long> featuresId;
    private Set<Image> images;
    private CounterDTO counter;
    private List<ReservationDTO> reservationList;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Time earliestCheckInHour;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Time latestCheckInHour;

    public TourDTO(Tour tour) {
        this.id = tour.getId();
        this.tourName = tour.getTourName();
        this.tourDescription = tour.getTourDescription();
        this.tourClassification = tour.getTourClassification();
        this.tourCapacity = tour.getTourCapacity();
        this.tourAvailability = tour.getTourAvailability();
        this.tourPrice = tour.getTourPrice();
        this.features = tour.getFeatures();
        if (tour.getCounter() != null) {
            this.counter = new CounterDTO(tour.getCounter());
        }
    }

    private List<ReservationDTO> convertReservationsToReservationsDTO(List<Reservation> reservationList) {
        if (reservationList == null) {
            return new ArrayList<>();
        } else {
            List<ReservationDTO> calendar = new ArrayList<ReservationDTO>();
            LocalDate today = LocalDate.now();
            for (Reservation reservation : reservationList
            ) {
                boolean reservationAlreadyEnded = today.isBefore(reservation.getFinalDate());
                if (reservationAlreadyEnded) {
                    ReservationDTO interval = new ReservationDTO(reservation);
                    calendar.add(interval);
                }
            }
            return calendar;
        }
    }

    // Este método convierte una lista de objetos Reservation en una lista de objetos ReservationDTO.
    // El método filtra las reservas que aún no han finalizado y las convierte en objetos ReservationDTO,
    // esto puede ser útil para mostrar únicamente las reservas activas o futuras en una interfaz de usuario
    // o para realizar algún tipo de procesamiento o cálculo basado en las reservas activas.


}
