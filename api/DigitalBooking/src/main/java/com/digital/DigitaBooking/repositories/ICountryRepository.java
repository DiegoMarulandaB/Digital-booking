package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICountryRepository extends JpaRepository<Country, Integer> {

    @Query(value = "SELECT c.* FROM country c WHERE c.country_name LIKE :country_name", nativeQuery = true)
    Country searchCountryByName(@Param("country_name") String country_name);

    // La consulta busca y devuelve un registro de país cuyo nombre coincida parcialmente con el parámetro
    // "country_name" proporcionado.

    @Query(value = "SELECT c.* FROM country c ORDER BY ABS(c.latitude - :userLatitude) + ABS(c.longitude - :userLongitude) ASC",nativeQuery = true)
    List<Country> searchCountriesByProximity(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude);

}