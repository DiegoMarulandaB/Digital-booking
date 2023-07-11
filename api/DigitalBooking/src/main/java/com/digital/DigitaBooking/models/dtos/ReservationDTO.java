package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationDTO {

    private Long id;
    private LocalDate initialDate;
    private LocalDate finalDate;

    private LocalTime startTime;

    private Long idUser;

    private Long idTour;

    public ReservationDTO(Reservation reservation) {
        LocalDate today = LocalDate.now();
        boolean isReservationFinished = today.isBefore(reservation.getFinalDate());
        if (isReservationFinished) {
            this.initialDate = reservation.getInitialDate();
            this.finalDate = reservation.getFinalDate();
        }
    }
    // Aquí se buscan establecer las fechas de inicio y finalización de una reserva en un objeto ReservationDTO,
    // solo si la reserva aún no ha finalizado según la fecha actual, es decir, se encarga de filtrar y crear
    // un objeto ReservationDTO solo para las reservas que aún no han finalizado, ignorando aquellas que ya han
    // pasado su fecha de finalización.

}