package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.CountryDTO;
import com.digital.DigitaBooking.models.dtos.CountryDistanceDTO;
import com.digital.DigitaBooking.models.entities.Country;
import com.digital.DigitaBooking.repositories.ICountryRepository;
import com.digital.DigitaBooking.services.ICountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private ICountryRepository countryRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void saveCountry(CountryDTO countryDTO) {

        Country country = mapper.convertValue(countryDTO, Country.class);
        countryRepository.save(country);
    }

    @Override
    public CountryDTO getCountry(Integer id) throws BadRequestException{
        if (id == null){
            throw new BadRequestException("El id de busqueda no puede ser nulo");
        }
        Optional<Country> country = countryRepository.findById(id);
        CountryDTO countryDTO = null;
        if (!country.isPresent()){
            throw  new BadRequestException("el pais con id "+id+" no existe");
        }
        countryDTO = mapper.convertValue(country, CountryDTO.class);

        return countryDTO;
    }

    @Override
    public void updateCountry(Integer id, CountryDTO countryDTO) {
        Optional<Country> optionalCountry = countryRepository.findById(id).map(country -> {
            country.setCountryName(countryDTO.getCountryName());
            country.setCapitalName(countryDTO.getCapitalName());
            country.setLatitude(countryDTO.getLatitude());
            country.setLongitude(countryDTO.getLongitude());
            return countryRepository.save(country);
        });
    }

    @Override
    public void deleteCountry(Integer id) {

        countryRepository.deleteById(id);
    }

    @Override
    public Set<CountryDTO> getCountries() {
        List<Country> countries = countryRepository.findAll();
        Set<CountryDTO> countriesDTO = new HashSet<>();
        for (Country country :
                countries) {
            countriesDTO.add(mapper.convertValue(country, CountryDTO.class));

        }
        return countriesDTO;
    }

    @Override
    public CountryDTO searchCountryByName(String countryName) throws BadRequestException {
        Country country = countryRepository.searchCountryByName(countryName);
        if (country != null) {
            return convertCountryToDTO(country);
        } else {
            throw new BadRequestException("No existe un país con el nombre: " + countryName);
        }
    }

    public List<CountryDistanceDTO> searchCountryByDistance(Double userLatitude, Double userLongitude){
        List<Country> countries = countryRepository.searchCountriesByProximity(userLatitude,userLongitude);
        List<CountryDistanceDTO> countryDistanceDTOList = new ArrayList<>();
        for(Country country: countries){
            CountryDistanceDTO countryDistanceDTO = new CountryDistanceDTO();
            countryDistanceDTO.setId(country.getId());
            countryDistanceDTO.setCountryName(country.getCountryName());
            countryDistanceDTOList.add(countryDistanceDTO);
        }
        return countryDistanceDTOList;
    }

    private CountryDTO convertCountryToDTO(Country country) {
        return mapper.convertValue(country, CountryDTO.class);
    }



}
