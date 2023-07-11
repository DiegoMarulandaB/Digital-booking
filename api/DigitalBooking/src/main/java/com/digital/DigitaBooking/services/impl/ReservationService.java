package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.converters.ReservationToReservationDTO;
import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.ReservationDTO;
import com.digital.DigitaBooking.models.entities.Reservation;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.repositories.IReservationRepository;
import com.digital.DigitaBooking.services.IReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
public class ReservationService implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationToReservationDTO reservationToReservationDTO;

    @Override
    public Reservation saveReservation(ReservationDTO reservationDTO) throws BadRequestException {
        boolean IdAssigned = reservationDTO.getId() != null;
        LocalTime maxTime = LocalTime.MAX; // Esto representa el límite superior permitido para un tiempo.
        boolean validStartTime = reservationDTO.getStartTime().compareTo(maxTime) <= 0;
        // El método compareTo() devuelve un valor negativo si reservationDTO.getStartTime() es anterior a
        // maxTime, cero si son iguales y un valor positivo si reservationDTO.getStartTime() es posterior
        // a maxTime.
        // Con esto verificamos si el tiempo de inicio en reservationDTO está dentro del rango válido,
        // considerando LocalTime.MAX como el límite superior permitido. Si validStartTime es true, significa
        // que el tiempo de inicio es válido.
        boolean validInterval = reservationDTO.getInitialDate().isBefore(reservationDTO.getFinalDate());
        validInterval &= !(reservationDTO.getInitialDate().isBefore(LocalDate.now()));
        // Se está verificando que el intervalo de reserva sea válido, es decir, que la fecha inicial no sea
        // anterior a la fecha actual.
        if (IdAssigned) {
            throw new BadRequestException("Ya existe una reserva para el ID asignado.");
        }
        if (!validInterval) {
            throw new BadRequestException("El intervalo de reserva no es válido.");
        }
        if (!validStartTime) {
            throw new BadRequestException("La hora de inicio no es válida.");
        }
        Tour tour = tourService.searchTourByIdAsClass(reservationDTO.getIdTour());
        System.out.println("----------------------------------------------------");
        System.out.println(tour.toString());
        System.out.println("----------------------------------------------------");
        User user = userService.searchUserByIdAsClass(reservationDTO.getIdUser());
        System.out.println(user.toString());
        boolean isInitialDateValidRange = !reservationDTO.getStartTime().isBefore(tour.getEarliestCheckInHour().toLocalTime());
        if (!isInitialDateValidRange) {
            throw new BadRequestException("La hora de check-in no es posible asignarla.");
            // Verifica si el tiempo de inicio de la reserva es igual o posterior a la hora de check-in más
            // temprana. Si es así, se considera que el rango de fechas de inicio es válido y
            // isInitialDateValidRange se establecerá en true.
        }
        boolean isIntervalDateAvailable = true;
        for (Reservation previousReservation : tour.getReservations()) {
            System.out.println(previousReservation.toString());
            boolean distinct = !reservationDTO.getInitialDate().isBefore(previousReservation.getFinalDate());
            distinct |= !reservationDTO.getFinalDate().isAfter(previousReservation.getInitialDate());
            isIntervalDateAvailable &= distinct;
            // Se verifica si el intervalo de fechas de la reserva actual no se superpone con ninguna de las
            // reservas anteriores del tour, y actualiza la variable isIntervalDateAvailable en función de
            // esta verificación. Si en algún momento se encuentra una superposición, la variable se establece
            // en false, indicando que el intervalo de fechas no está disponible.
        }
        if (!isIntervalDateAvailable) {
            throw new BadRequestException("El intervalo de fechas no está disponible porque ya ha sido ocupado.");
        } else {
            Reservation reservation = new Reservation();
            reservation.setInitialDate(reservationDTO.getInitialDate());
            reservation.setFinalDate(reservationDTO.getFinalDate());
            reservation.setStartTime(reservationDTO.getStartTime());
            reservation.setTour(tour);
            reservation.setUser(user);
            Reservation saved = reservationRepository.save(reservation);
            tour.addReservation(saved);
            user.addReservation(saved);
            return saved;
        }
    }

    @Override
    public Reservation getReservationById(Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("El ID de la reserva es nulo.");
        }
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            return optionalReservation.get();
        } else {
            throw new BadRequestException("No existe una reserva con el ID " + id + ".");
        }
    }

    @Override
    public Boolean deleteReservation(Long id) throws BadRequestException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            reservationRepository.deleteById(id);
            return true;
        } else {
            throw new BadRequestException("No existe una reserva con el ID " + id + ".");
        }
    }

    @Override
    public List<ReservationDTO> getReservationsByTour(Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("El ID del tour es nulo.");
        }
        try {
            tourService.getTour(id);
        } catch (Exception e) {
            throw new BadRequestException("El ID del tour no corresponde con un tour almacenado en la base de datos.");
        }
        List<Reservation> results = reservationRepository.getReservationByTourId(id);
        setUsersAsNull(results);
        List<ReservationDTO> reservations = new ArrayList<>();
        for(Reservation reservation: results){
            reservations.add(reservationToReservationDTO.convert(reservation));
        }
        return reservations;
    }
    // El objetivo de este método es obtener una lista de reservas asociadas a un tour específico.

    @Override
    public List<ReservationDTO> getReservationsByUser(Long id) throws BadRequestException {
        if (id == null) {
            throw new BadRequestException("El ID del usuario es nulo.");
        }
        try {
            userService.getUser(id);
        } catch (Exception e) {
            throw new BadRequestException("El ID del usuario no corresponde con un usuario almacenado en la base de datos.");
        }
        List<ReservationDTO> reservations = new ArrayList<>();
        List<Reservation> results = reservationRepository.getReservationByUserId(id);
        for(Reservation reservation: results){
            reservations.add(reservationToReservationDTO.convert(reservation));
        }
        return reservations;
    }

    private void setUsersAsNull(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.setUser(null);
        }
    }

    // Con este método desvinculamos las referencias a los usuarios en las reservas, estableciendo el
    // campo user como null. Esto puede ser útil en situaciones donde se desea eliminar la relación entre
    // las reservas y los usuarios, o cuando se necesita limpiar o reiniciar el campo user en las
    // reservas.
}