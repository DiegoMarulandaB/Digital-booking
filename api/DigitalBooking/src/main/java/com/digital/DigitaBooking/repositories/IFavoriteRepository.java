package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {
}
