package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.ScoreDTO;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.models.entities.score.Score;

import java.util.List;

public interface IScoreService {

    Score saveScore(ScoreDTO scoreDTO, User user) throws BadRequestException;

    ScoreDTO getVote(Long id) throws BadRequestException;

    ScoreDTO updateVote(ScoreDTO scoreDTO) throws BadRequestException;

    boolean deleteVote(Long id) throws BadRequestException;

    List<ScoreDTO> getVotesFromTourWithId(Long tourId);
}
