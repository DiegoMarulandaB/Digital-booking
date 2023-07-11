package com.digital.DigitaBooking.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface IJwtUtil {

    String extractUserName(String token, String secretKey);

    String generateToken(UserDetails userDetails, long systemCurrentMillis,
                         long configuredExpirationTimeInMillis,
                         String secretKey);

    Date extractExpiration(String token, String secretKey);
}

// * extractUserName recibe un token JWT y una clave secreta (secretKey) y se
// encarga de extraer y devolver el nombre de usuario contenido en el token.
// * generateToken genera un nuevo token JWT utilizando la información proporcionada y
// lo devuelve como una cadena de caracteres.
// * extractExpiration extrae y devuelve la fecha de expiración del token en formato Date,
// lo cual permite determinar cuándo expirará el token.