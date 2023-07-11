package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Tour;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;


import java.util.List;
import java.util.Optional;

@Repository
public interface ITourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findAllByCategoryId(Integer categoryId);

    @Query(value = "SELECT t FROM Tour t WHERE t.country.id = ?1")
    List<Tour> findAllToursByCountry(Integer id);

    // @Query Indica que se trata de una consulta personalizada escrita en JPQL (Java Persistence Query Language).
    // "SELECT t FROM tour t" especifica que se seleccionarán entidades de la clase Tour y se asigna el alias t
    // para referirse a cada objeto Tour.
    // Cuando usamos la expresión t.country.id estamos accediendo al identificador del país que está asociado
    // al objeto Tour.
    // En la consulta, el ?1 es un marcador de posición para un parámetro. Los marcadores de posición se
    // utilizan para indicar que se espera recibir un valor para ese parámetro en el momento de ejecutar la
    // consulta.

    @Query(value = "SELECT t.*, c.country_name FROM tour t INNER JOIN country c ON c.id = t.country WHERE c.country_name LIKE %:country_name%", nativeQuery = true)
    List<Tour> findAllToursByCountryName(@Param("country_name") String country_name);

    // La consulta proporcionada busca tours basados en el nombre parcial de un país.
    // Por ejemplo, si se proporciona el valor "Uni" como filtro, la consulta buscará tours cuyo nombre de país
    // contenga la cadena "Uni", como "United States", "United Kingdom", "Tunisia", etc.

    @Query(value = "SELECT t.* FROM tour t " + "WHERE t.country = :countryId " + "AND t.id NOT IN ( " +
            "SELECT DISTINCT r.id_tour " + "FROM reservation r " + " WHERE (r.final_date > :initialDate AND r.initial_date < :finalDate) " + ")" +
            "GROUP BY t.id", nativeQuery = true)
    List<Tour> findToursByCountryAndDates(@Param("countryId") Integer countryId, @Param("initialDate") LocalDate initialDate, @Param("finalDate") LocalDate finalDate);

    // Esta consulta busca tours en un país específico (countryId) y excluye aquellos que tienen reservas que se
    // superponen con las fechas proporcionadas (initialDate y finalDate).

    @Query(value = "SELECT t.* FROM tour t " +
            "INNER JOIN country c ON t.country = c.id " +
            "ORDER BY (6371 * acos(" +
            "cos(radians(:userLatitude)) * " +
            "cos(radians(c.latitude)) * " +
            "cos(radians(c.longitude) - radians(:userLongitude)) + " +
            "sin(radians(:userLatitude)) * " +
            "sin(radians(c.latitude)) " +
            "))", nativeQuery = true)
    List<Tour> searchToursByProximity(@Param("userLatitude") Double userLatitude, @Param("userLongitude") Double userLongitude);

}
