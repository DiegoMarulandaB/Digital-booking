package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFeatureRepository extends JpaRepository<Feature, Long> {
}
