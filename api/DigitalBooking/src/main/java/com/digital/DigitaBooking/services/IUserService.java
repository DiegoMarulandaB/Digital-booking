package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.PageResponseDTO;
import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.models.dtos.UserSignUp;
import com.digital.DigitaBooking.models.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    UserDetails registerUser(UserSignUp userSignUp);

    // Este método se utiliza para registrar un nuevo usuario en el sistema utilizando la
    // información proporcionada en el objeto "UserSignUp". Retorna los detalles del
    // usuario registrado.
    PageResponseDTO<UserDTO> getUsers(Pageable pageable);
    // Este método se utiliza para obtener una lista paginada de usuarios

    UserDTO getUser(Long id);

    User searchUserByIdAsClass(Long id) throws BadRequestException;

}

// Esta interfaz define métodos para registrar usuarios y obtener usuarios en una
// aplicación. La implementación de esta interfaz proporcionará la lógica concreta para
// realizar estas operaciones según los requisitos específicos del sistema.