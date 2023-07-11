package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.CountryDTO;
import com.digital.DigitaBooking.models.dtos.ReservationDTO;
import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.models.entities.Reservation;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.services.impl.ReservationService;
import com.digital.DigitaBooking.services.impl.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationDTO reservationDTO) throws BadRequestException {
        Reservation savedReservation = reservationService.saveReservation(reservationDTO);
        return new ResponseEntity<Reservation>(savedReservation, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) throws BadRequestException {
        Reservation foundReservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(foundReservation);
    }

    @GetMapping(path = "/byTour/{id}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByTour(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(reservationService.getReservationsByTour(id));
    }

    @GetMapping(path = "/byUser/{id}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(reservationService.getReservationsByUser(id));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) throws BadRequestException {
        if (reservationService.deleteReservation(id)) {
            return ResponseEntity.ok("Se eliminó la reserva con ID: " + id);
        } else {
            return new ResponseEntity("No existe una reserva con este ID.", HttpStatus.NOT_FOUND);
        }
    }
}
