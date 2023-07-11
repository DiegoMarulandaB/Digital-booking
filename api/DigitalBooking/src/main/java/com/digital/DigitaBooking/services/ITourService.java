package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.TourDTO;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.util.TourFilter;


import java.util.List;
import java.util.Set;

public interface ITourService {

    Tour saveTour(TourDTO tourDTO);

    TourDTO getTour(Long id);

    void updateTour(Long id, TourDTO tourDTO);

    void deleteTour(Long id);

    Set<TourDTO> getTours();

    Set<TourDTO> getToursByCategory(Integer id);

    Set<TourDTO> getToursByCountry(Integer id);

    List<TourDTO> findAllToursByCountryName(String countryName) throws BadRequestException;

    List<TourDTO> findToursByCountryAndDates(TourFilter tourFilter) throws BadRequestException;

    Tour searchTourByIdAsClass(Long id) throws BadRequestException;

}
