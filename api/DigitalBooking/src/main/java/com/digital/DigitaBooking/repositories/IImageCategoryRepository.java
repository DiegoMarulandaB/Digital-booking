package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.ImageCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageCategoryRepository extends JpaRepository<ImageCategory, Integer> {
}
