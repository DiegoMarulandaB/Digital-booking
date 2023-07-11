package com.digital.DigitaBooking.models.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizationResponse {

    private String jwt;
}

// Esta clase representa la respuesta de autorización y tiene un único atributo "jwt" que
// almacena el token JWT generado.
// Un token JWT es una cadena de caracteres utilizada para autenticar y autorizar a un
// usuario en una aplicación web o servicio.