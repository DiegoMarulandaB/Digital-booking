package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.converters.UserToUserDTOConverter;
import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.PageResponseDTO;
import com.digital.DigitaBooking.models.dtos.UserSignUp;
import com.digital.DigitaBooking.models.entities.Role;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.repositories.IUserRepository;
import com.digital.DigitaBooking.services.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    // PasswordEncoder es una interfaz que proporciona métodos para codificar y verificar
    // contraseñas de manera segura. Se utiliza en el contexto de autenticación y
    // almacenamiento seguro de contraseñas.
    private final ConversionService conversionService;

    private final UserToUserDTOConverter userToUserDTOConverter;
    // ConversionService es una interfaz que proporciona métodos para convertir objetos
    // de un tipo a otro, de acuerdo con las reglas de conversión configuradas. Como por
    // ejemplo para convertir objetos DTO a entidades de dominio o viceversa.

    @Override
    public UserDetails registerUser(UserSignUp userSignUp) {
        try {
            return userRepository.save(User.builder()
                    .userName(userSignUp.getUserName())
                    .userFirstName(userSignUp.getUserFirstName())
                    .userLastName(userSignUp.getUserLastName())
                    .latitude(userSignUp.getLatitude())
                    .longitude(userSignUp.getLongitude())
                    .password(passwordEncoder.encode(userSignUp.getPassword())) // Se utiliza para encriptar la contraseña antes de almacenarla en la base de datos.
                    .role(Role.USER) // Se asigna el rol del usuario registrado.
                    .build()); // Construye y retorna una instancia completa de la clase User con los datos proporcionados.
        } catch (
                DataIntegrityViolationException e) { // Esta excepción se lanza si ocurre una violación de integridad de datos al intentar guardar el usuario en la base de datos. Por ejemplo, si se intenta registrar un usuario con un nombre de usuario que ya existe.
            throw new ErrorResponseException(HttpStatus.CONFLICT,
                    ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                            "User already registered"), e);
        }
    }

    public UserDetails makeAdmin(UserSignUp userSignUp) {
        try {
            User user = userRepository.getFirstByEmail(userSignUp.getUserName());
            Role role = null;
            user.setRole(role.ADMIN);
            return userRepository.save(user); // Se asigna el rol del usuario registrado.

        } catch (
                DataIntegrityViolationException e) { // Esta excepción se lanza si ocurre una violación de integridad de datos al intentar guardar el usuario en la base de datos. Por ejemplo, si se intenta registrar un usuario con un nombre de usuario que ya existe.
            throw new ErrorResponseException(HttpStatus.CONFLICT,
                    ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                            "User Not found"), e);
        }
    }

    public UserDetails makeUser(UserSignUp userSignUp) {
        try {
            User user = userRepository.getFirstByEmail(userSignUp.getUserName());
            Role role = null;
            user.setRole(role.USER);
            return userRepository.save(user); // Se asigna el rol del usuario registrado.

        } catch (
                DataIntegrityViolationException e) { // Esta excepción se lanza si ocurre una violación de integridad de datos al intentar guardar el usuario en la base de datos. Por ejemplo, si se intenta registrar un usuario con un nombre de usuario que ya existe.
            throw new ErrorResponseException(HttpStatus.CONFLICT,
                    ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                            "U/ser Not Found"), e);
        }
    }


    @Override
    public PageResponseDTO<UserDTO> getUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable); // Devuelve un objeto Page que contiene la lista de usuarios y la información de paginación.
        return new PageResponseDTO<>(
                userPage.getContent().stream()
                        .map(user -> conversionService.convert(user, UserDTO.class)).toList() // Se toma la lista de usuarios, se convierte cada usuario a su equivalente en UserDTO utilizando el servicio de conversión y se recopilan los resultados en una lista.
                , userPage.getPageable()
                , userPage.getTotalElements());
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.getFirstByEmail(userName);
        if (userDetails == null) {
            throw new UsernameNotFoundException(userName);
        }
        return userDetails;
    }

    // loadByUsername  se utiliza para cargar los detalles de un usuario basado en su
    // nombre de usuario.

    @Autowired
    ObjectMapper mapper;

    @Override
    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        UserDTO userDTO = null;
        if (user.isPresent())
            userDTO = userToUserDTOConverter.convert(user.get());

        return userDTO;
    }

    @Override
    public User searchUserByIdAsClass(Long id) throws BadRequestException {
        Optional<User> optionalUser = userRepository.findById(id);
        System.out.println(optionalUser);
        System.out.println(optionalUser.get());
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new BadRequestException("No existe el usuario con ID " + id);
        }
    }
}
