package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.models.dtos.PageResponseDTO;
import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.models.dtos.UserSignUp;
import com.digital.DigitaBooking.models.dtos.UserPageDTO;
import com.digital.DigitaBooking.services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserSignUp userSignUp) {
        userService.registerUser(userSignUp);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path="/name/{username}")
    @PreAuthorize("hasRole('ADMIN','USER')")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable("username") String username) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return ResponseEntity.ok(userDetails);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN','USER')")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // Método de controlador utilizando anotaciones de Swagger y Spring Framework.
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retrieve a paginated list of users",
            parameters = {@Parameter(in = ParameterIn.QUERY, name = "page", description = "Page"),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Size")},
            responses = {@ApiResponse(responseCode = "200", description = "Request processed successfully",
                    content = @Content(mediaType = "application(json", schema = @Schema(implementation = UserPageDTO.class)))})

    // * security: Especifica los requisitos de seguridad para la operación. En este caso,
    // se requiere el uso de un "bearer-key" para autenticación.
    // Tenemos 2 parámetros definidos utilizando la anotación @Parameter ("page" y "size").
    // * in: Indica la ubicación del parámetro. En este caso, ambos parámetros son de tipo
    // QUERY, lo que significa que se enviarán en la URL como parámetros de consulta.
    // En responses tenemos un content que en nuestro caso se establece como
    // "application/json" para indicar que la respuesta será en formato JSON.
    // Por otro lado, tenemos schema que define la estructura del objeto de respuesta.
    // En este caso, se utiliza la clase UserPageDTO para representar la lista paginada
    // de usuarios.

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<UserDTO> getUsers(@PageableDefault(size = 10, page = 0) @ParameterObject Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeAdmin(@RequestBody UserSignUp userSingup) {
        userService.makeAdmin(userSingup);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeUser(@RequestBody UserSignUp userSingup) {
        userService.makeUser(userSingup);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    // Este método obtiene una lista paginada de usuarios y la devuelve como una respuesta.
}
