package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT r.* FROM reservation r WHERE r.id_tour = :tourId", nativeQuery = true)
    List<Reservation> getReservationByTourId(@Param("tourId") Long tourId);

    @Query(value = "SELECT r.* FROM reservation r WHERE r.id_user = :userId ORDER BY r.initial_date DESC", nativeQuery = true)
    List<Reservation> getReservationByUserId(@Param("userId") Long userId);
}
