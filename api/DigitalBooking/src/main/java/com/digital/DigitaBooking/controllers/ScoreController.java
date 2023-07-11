package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.ScoreDTO;
import com.digital.DigitaBooking.models.dtos.ScoreRequest;
import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.models.entities.Reservation;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.models.entities.score.Score;
import com.digital.DigitaBooking.services.impl.ScoreService;
import com.digital.DigitaBooking.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Score> saveScore(@RequestBody ScoreRequest request) throws BadRequestException {
        User user = convertUserDTOToUser(request.getUserDTO());
        Score savedScore = scoreService.saveScore(request.getScoreDTO(), user);
        return new ResponseEntity<Score>(savedScore, HttpStatus.CREATED);
    }

    private User convertUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        return user;

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ScoreDTO> getVote(@PathVariable Long id, HttpServletRequest request) throws BadRequestException {
        ScoreDTO scoreDTO = scoreService.getVote(id);
        return ResponseEntity.ok(scoreDTO);
    }

    @GetMapping(path = "fromTour/{id}")
    public ResponseEntity<List<ScoreDTO>> getVotesFromTourWithId(@PathVariable Long tourId) {
        List<ScoreDTO> results = scoreService.getVotesFromTourWithId(tourId);
        return ResponseEntity.ok(results);
    }

    @PutMapping(path = "/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ScoreDTO> updateVote(@RequestBody ScoreDTO scoreDTO) throws BadRequestException {
        ScoreDTO updatedVote = scoreService.updateVote(scoreDTO);
        return ResponseEntity.ok(updatedVote);
    }

    @DeleteMapping(path = "delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteVote(@PathVariable Long id) throws BadRequestException {
        if (id != null) {
            if (scoreService.deleteVote(id)) {
                return ResponseEntity.ok("El voto con ID: " + id + " fue eliminado.");
            } else {
                return new ResponseEntity<>("El voto con ID: " + id + " es inexistente.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No hay información en el ID proporcionado", HttpStatus.NO_CONTENT);
        }

    }

}
