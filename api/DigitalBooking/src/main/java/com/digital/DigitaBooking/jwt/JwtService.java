package com.digital.DigitaBooking.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService implements IJwtService {

    private final JwtUtil jwtUtil;
    private final JwtConfiguration jwtConfiguration;
    private final Clock clock;

    @Override
    public String extractUserName(String token) {
        return jwtUtil.extractUserName(token, jwtConfiguration.secretKey());
    }

    // Extrae el nombre de usuario de un token JWT.

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtUtil.generateToken(userDetails,
                clock.millis(),
                jwtConfiguration.expiration(),
                jwtConfiguration.secretKey());
    }

    // Genera un nuevo token JWT basado en los detalles del usuario.
    private boolean isTokenExpired(String token) {
        return jwtUtil.extractExpiration(token, jwtConfiguration.secretKey()).before(new Date(clock.millis()));
    }

    // Obtiene la fecha de expiración de un token JWT y la compara con la fecha actual
    // para determinar si el token ha expirado.

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, String username) {
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Verifica si un token JWT es válido para un usuario específico. Se realiza una
    // verificación en dos etapas: primero se compara el nombre de usuario y luego se
    // verifica si el token ha expirado.
}
