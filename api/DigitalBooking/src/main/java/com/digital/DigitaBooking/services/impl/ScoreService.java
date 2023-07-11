package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.ScoreDTO;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.models.entities.score.Counter;
import com.digital.DigitaBooking.models.entities.score.Score;
import com.digital.DigitaBooking.repositories.ICounterRepository;
import com.digital.DigitaBooking.repositories.IScoreRepository;
import com.digital.DigitaBooking.services.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoreService implements IScoreService {

    private IScoreRepository scoreRepository;

    private ICounterRepository counterRepository;

    @Autowired
    public ScoreService(IScoreRepository scoreRepository, ICounterRepository counterRepository) {
        this.scoreRepository = scoreRepository;
        this.counterRepository = counterRepository;
    }

    @Override
    public Score saveScore(ScoreDTO scoreDTO, User user) throws BadRequestException {
        boolean hasRequiredValues = scoreDTO.getValue() != null && scoreDTO.getScoreId() != null;
        if (!hasRequiredValues) {
            throw new BadRequestException("El voto carece de los valores requeridos, como el valor numérico y/o ID.");
        }
        boolean idIsAlreadyAsigned = scoreDTO.getId() != null;
        if (idIsAlreadyAsigned) {
            throw new BadRequestException("El voto ya tiene un ID asignado.");
        }
        boolean invalidValue = scoreDTO.getValue() > 5 || scoreDTO.getValue() < 1;
        if (invalidValue) {
            throw new BadRequestException("El valor del voto no es válido, debe ser un valor entre 1 y 5.");
        }
        boolean alreadyVoted = userAlreadyVotedOn(user, scoreDTO.getScoreId());
        if (alreadyVoted) {
            throw new BadRequestException("El usuario " + user + " ya ha realizado una votación para este tour.");
        }

        Counter counter = getCounterById(scoreDTO.getScoreId());
        Score toSave = new Score();
        toSave.setValue(scoreDTO.getValue());
        toSave.setCounter(counter);
        toSave.setUser(user);
        counter.addVote(toSave.getValue());
        return scoreRepository.save(toSave);
    }

    Counter getScore(Long scoreId) throws BadRequestException {
        if (scoreId == null) {
            throw new BadRequestException("El ID de la puntuación es nulo.");
        }
        Optional<Counter> optionalScore = counterRepository.findById(scoreId);
        if (optionalScore.isPresent()) {
            return optionalScore.get();
        } else {
            throw new BadRequestException("El ID del contador de puntuación es inválido o no existe en la base de datos.");
        }
    }

    private boolean userAlreadyVotedOn(User user, Long counterId) {
        for (Score score : user.getScores()) {
            if (score.getCounter().getId().equals(counterId)) {
                return true;
            }
        }
        return false;
    }

    private Counter getCounterById(Long counterId) throws BadRequestException {
        Optional<Counter> optionalCounter = counterRepository.findById(counterId);
        if (optionalCounter.isEmpty()) {
            throw new BadRequestException("No existe ese contador de votos en la base de datos.");
        }
        return optionalCounter.get();
    }

    @Override
    public ScoreDTO getVote(Long id) throws BadRequestException {
        return new ScoreDTO(searchScoreByIdAsClass(id));
    }

    public Score searchScoreByIdAsClass(Long id) throws BadRequestException {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if (optionalScore.isPresent()) {
            return optionalScore.get();
        } else {
            throw new BadRequestException("El ID del voto es inexistente.");
        }
    }

    @Override
    public ScoreDTO updateVote(ScoreDTO scoreDTO) throws BadRequestException {
        Score voteInDatabase = searchScoreByIdAsClass(scoreDTO.getId());
        if (scoreDTO.getValue() == null) {
            throw new BadRequestException("El valor del voto es nulo.");
        }
        if (scoreDTO.getValue() > 5 || scoreDTO.getValue() < 1) {
            throw new BadRequestException("El valor del voto no es válido, debe ser un valor entre 1 y 5.");
        }
        boolean valueChange = !voteInDatabase.getValue().equals(scoreDTO.getValue());
        if (valueChange) {
            Counter counter = getCounterById(scoreDTO.getScoreId());
            counter.discountVote(voteInDatabase.getValue());
            counter.discountVote(scoreDTO.getValue());
            voteInDatabase.setValue(scoreDTO.getValue());
            counterRepository.save(counter);
            scoreRepository.save(voteInDatabase);
        }
        return scoreDTO;
    }

    @Override
    public boolean deleteVote(Long id) throws BadRequestException {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if (optionalScore.isEmpty()) {
            throw new BadRequestException("No se encuentra registrado ningún voto con el ID proporcionado.");
        }
        Score scoreToDelete = optionalScore.get();
        Counter counter = scoreToDelete.getCounter();
        counter.discountVote(scoreToDelete.getValue());
        scoreRepository.deleteById(id);
        return true;
    }

    @Override
    public List<ScoreDTO> getVotesFromTourWithId(Long tourId) {
        List<Score> result = scoreRepository.getAllScoresByTourId(tourId);
        return result.stream().map(ScoreDTO::new).collect(Collectors.toList());
    }
    // El código busca obtener una lista de objetos ScoreDTO que representan los votos relacionados con un
    // tour específico.
    // En este método usamos el scoreRepository para recuperar una lista de votos utilizando el método
    // getAllScoresByTourId(tourId). Luego, se aplica una transformación a cada elemento de la lista
    // utilizando el método map(ScoreDTO::new), lo que crea una nueva instancia de ScoreDTO para cada objeto
    // Score en la lista.
    // El uso de stream() junto con map() y collect() permite aplicar una transformación a cada elemento de
    // la lista de votos y recopilar los resultados transformados en una nueva lista. Esto simplifica el
    // proceso de manipulación y transformación de los datos de la lista.
}
