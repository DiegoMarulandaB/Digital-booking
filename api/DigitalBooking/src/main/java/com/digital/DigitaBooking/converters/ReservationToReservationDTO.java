package com.digital.DigitaBooking.converters;

import com.digital.DigitaBooking.models.dtos.ReservationDTO;
import com.digital.DigitaBooking.models.entities.Reservation;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReservationToReservationDTO implements Converter<Reservation, ReservationDTO> {


    @Override
    public ReservationDTO convert(Reservation source) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(source.getId());
        reservationDTO.setStartTime(source.getStartTime());
        reservationDTO.setInitialDate(source.getInitialDate());
        reservationDTO.setFinalDate(source.getFinalDate());
        reservationDTO.setIdTour(source.getTour().getId());
        return reservationDTO;
    }
}
