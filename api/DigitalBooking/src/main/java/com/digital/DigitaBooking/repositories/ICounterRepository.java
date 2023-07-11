package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.score.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICounterRepository extends JpaRepository<Counter, Long> {
}
