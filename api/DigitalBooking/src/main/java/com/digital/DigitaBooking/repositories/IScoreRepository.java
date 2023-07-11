package com.digital.DigitaBooking.repositories;

import com.digital.DigitaBooking.models.entities.score.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScoreRepository extends JpaRepository<Score, Long> {
    @Query(value = "SELECT s.* FROM score s WHERE s.id_counter = (" +
            "SELECT counter.id FROM counter " +
            "WHERE counter.id_tour = :tourId)", nativeQuery = true)
    List<Score> getAllScoresByTourId(Long tourId);
}
