package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.converters.TourToTourDTOConverter;
import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.CountryDTO;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.Country;
import com.digital.DigitaBooking.models.entities.Feature;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.models.dtos.TourDTO;
import com.digital.DigitaBooking.models.entities.score.Counter;
import com.digital.DigitaBooking.repositories.*;
import com.digital.DigitaBooking.services.ICountryService;
import com.digital.DigitaBooking.services.ITourService;
import com.digital.DigitaBooking.util.TourFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourService implements ITourService {

    @Autowired
    private ITourRepository tourRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IFeatureRepository featureRepository;

    @Autowired
    private ICountryRepository countryRepository;

    @Autowired
    private ICounterRepository counterRepository;

    @Autowired
    private CountryService countryService;


    @Autowired
    ObjectMapper mapper;

    @Autowired
    TourToTourDTOConverter tourConverter;

    @Override
    public Tour saveTour(TourDTO tourDTO) {
        System.out.println(tourDTO.toString());

        Tour tour = mapper.convertValue(tourDTO, Tour.class);
        System.out.println(tour.toString());
        Category category = categoryRepository.findById(tourDTO.getCategoryId()).get();
        Set<Feature> features = new HashSet<>();
        tour.setCategory(category);
        Country country = countryRepository.findById(tourDTO.getCountryId()).get();
        tour.setCountry(country);
        for (Long featureId : tourDTO.getFeaturesId()) {
            features.add(featureRepository.getReferenceById(featureId));
        }
        tour.setFeatures(features);
        Tour newTour = tourRepository.save(tour);
        return newTour;
    }

    @Override
    public TourDTO getTour(Long id) {
        Tour tour = tourRepository.findById(id).get();
        TourDTO tourDTO = null;
        tourDTO = tourConverter.convert(tour);
        return tourDTO;
    }

    @Override
    public void updateTour(Long id, TourDTO tourDTO) {

        Optional<Tour> optionalTour = tourRepository.findById(id).map(tour -> {
            System.out.println("entro al map");
            Set<Feature> features = new HashSet<>();
            tour.setTourName(tourDTO.getTourName());
            tour.setTourDescription(tourDTO.getTourDescription());
            tour.setTourClassification(tourDTO.getTourClassification());
            tour.setTourCapacity(tourDTO.getTourCapacity());
            tour.setTourPrice(tourDTO.getTourPrice());
            tour.setCountry(countryRepository.getById(tourDTO.getCountryId()));
            tour.updateCategory(categoryRepository.getById(tourDTO.getCategoryId()));
            tour.updateCountry(countryRepository.getById(tourDTO.getCountryId()));
            System.out.println(tourDTO.getFeaturesId());
            for (Long featureId : tourDTO.getFeaturesId()) {
                System.out.println("Entro a los features");
                features.add(featureRepository.getReferenceById(featureId));
            }
            tour.setFeatures(features);
            tour.setEarliestCheckInHour(tourDTO.getEarliestCheckInHour());
            tour.setLatestCheckInHour(tourDTO.getLatestCheckInHour());
            tourRepository.save(tour);
            return tour;
        });

    }

    @Override
    public void deleteTour(Long id) {

        tourRepository.deleteById(id);
    }

    @Override
    public Set<TourDTO> getTours() {
        List<Tour> tours = tourRepository.findAll();
        Set<TourDTO> toursDTO = new HashSet<>();
        for (Tour tour :
                tours) {
//            tour.addFeature();
            toursDTO.add(tourConverter.convert(tour));

        }
        return toursDTO;
    }

    @Override
    public Set<TourDTO> getToursByCategory(Integer id) {
        Set<TourDTO> toursDTO = new HashSet<>();
        List<Tour> tours = tourRepository.findAllByCategoryId(id);
        for (Tour tour :
                tours) {
            toursDTO.add(tourConverter.convert(tour));

        }
        return toursDTO;
    }

    @Override
    public Set<TourDTO> getToursByCountry(Integer id) {
        Set<TourDTO> toursDTO = new HashSet<>();
        List<Tour> tours = tourRepository.findAllToursByCountry(id);
        for (Tour tour :
                tours) {
            toursDTO.add(tourConverter.convert(tour));
        }
        return toursDTO;
    }

    @Override
    public List<TourDTO> findAllToursByCountryName(String countryName) throws BadRequestException {
        CountryDTO isCountryValid = countryService.searchCountryByName(countryName);
        if (isCountryValid == null) {
            throw new BadRequestException("País no encontrado.");
        }
        List<Tour> matchingTours = this.tourRepository.findAllToursByCountryName(countryName);
        return convertToDTOList(matchingTours);
    }

    @Override
    public List<TourDTO> findToursByCountryAndDates(TourFilter tourFilter) throws BadRequestException {
        boolean noNullData = tourFilter.getInitialDate() != null && tourFilter.getFinalDate() != null && tourFilter.getCountryId() != null;
        if (!noNullData) {
            throw new BadRequestException("El filtro no puede estar vacío.");
        }
        boolean datesInOrder = tourFilter.getFinalDate().isAfter(tourFilter.getInitialDate());
        boolean oldInitialDate = LocalDate.now().isAfter(tourFilter.getInitialDate());
        if (!datesInOrder) {
            throw new BadRequestException("Las fechas no están en orden correcto o son iguales.");
        }
        if (oldInitialDate) {
            throw new BadRequestException("La fecha inicial no puede ser anterior a la fecha actual.");
        }
        countryService.getCountry(tourFilter.getCountryId());
        List<Tour> results = tourRepository.findToursByCountryAndDates(tourFilter.getCountryId(), tourFilter.getInitialDate(), tourFilter.getFinalDate());
        return convertToDTOList(results);
    }

    @Override
    public Tour searchTourByIdAsClass(Long id) throws BadRequestException {
        Optional<Tour> optionalTour = tourRepository.findById(id);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            return tour;
        } else {
            throw new BadRequestException("No existe un tour con ID " + id);
        }
    }
    // Busca un tour en la base de datos por su ID y devuelve el objeto Tour correspondiente si se encuentra.


    private List<TourDTO> convertToDTOList(List<Tour> list) {
        return list.stream().map(tour -> tourConverter.convert(tour)).collect(Collectors.toList());
    }

    // Convertimos los objetos Tour en objetos TourDTO mediante un stream para recorrer la lista de tours
    // y el método map para aplicar la conversión a cada uno.

    public Set<TourDTO> getToursByCountryDistance(Double latitude, Double longitude){
        Set<TourDTO> toursDTO = new HashSet<>();
        List<Tour> tours = tourRepository.searchToursByProximity(latitude,longitude);
        for (Tour tour :
                tours) {
            toursDTO.add(tourConverter.convert(tour));

        }
        return toursDTO;
    }

    private Counter getPuntuationCounter(Long puntuationId) throws BadRequestException {
        Optional<Counter> optionalCounter = counterRepository.findById(puntuationId);
        if (optionalCounter.isEmpty()) {
            throw new BadRequestException("No existe el Counter ID.");
        } else {
            return optionalCounter.get();
        }
    }
}

