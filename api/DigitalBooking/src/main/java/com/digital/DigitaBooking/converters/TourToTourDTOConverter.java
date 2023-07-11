package com.digital.DigitaBooking.converters;

import com.digital.DigitaBooking.models.dtos.TourDTO;
import com.digital.DigitaBooking.models.entities.Tour;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.datatype.jsr310.*;
//import com.fasterxml.jackson.datatype.jackson-datatype-jsr310;

@Component
public class TourToTourDTOConverter implements Converter<Tour, TourDTO> {

    @Override
    public TourDTO convert(Tour tour) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(tour.getId());
        tourDTO.setTourName(tour.getTourName());
        tourDTO.setTourDescription(tour.getTourDescription());
        tourDTO.setTourClassification(tour.getTourClassification());
        tourDTO.setTourCapacity(tour.getTourCapacity());
        tourDTO.setTourAvailability(tour.getTourAvailability());
        tourDTO.setTourPrice(tour.getTourPrice());
        tourDTO.setCategoryId(tour.getCategory().getId());
        tourDTO.setCountryId(tour.getCountry().getId());
        tourDTO.setFeatures(tour.getFeatures());
        tourDTO.setImages(tour.getImages());
        tourDTO.setEarliestCheckInHour(tour.getEarliestCheckInHour());
        tourDTO.setLatestCheckInHour(tour.getLatestCheckInHour());
        return tourDTO;
    }
}
