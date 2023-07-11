package com.digital.DigitaBooking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil implements IJwtUtil {

    private Key getKey(String secretKey) {

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Este método utiliza la biblioteca io.jsonwebtoken.security.Keys para generar una
    // clave secreta (Key) a partir de un valor de clave (secretKey). Esta clave secreta
    // es necesaria para realizar operaciones de cifrado y descifrado relacionadas con JWT.

    private Claims extractAllClaims(String token, String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Este método utiliza la biblioteca JWT para analizar y verificar la validez de un
    // token JWT. Extrae todas las reclamaciones del token y las devuelve como un objeto
    // Claims. La clave secreta (secretKey) se utiliza para verificar la firma del token
    // y asegurar su integridad.

    @Override
    public String extractUserName(String token, String secretKey) {
        Claims claims = extractAllClaims(token, secretKey);
        return claims.getSubject();
    }

    // Este método utiliza el método extractAllClaims para obtener todas las reclamaciones
    // del token JWT. Luego, extrae y devuelve el nombre de usuario (subject) del token
    // utilizando el método getSubject() del objeto Claims. La clave secreta (secretKey)
    // se utiliza para verificar la firma del token y asegurar su integridad antes de
    // extraer las reclamaciones.

    @Override
    public String generateToken(UserDetails userDetails,
                                long systemCurrentMillis,
                                long configuredExpirationTimeInMillis,
                                String secretKey) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(systemCurrentMillis))
                .setExpiration(new Date(systemCurrentMillis + configuredExpirationTimeInMillis))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    // Este método utiliza la biblioteca JWT para construir y firmar un token JWT.
    // Se establecen el nombre de usuario, la fecha de emisión y la fecha de expiración
    // del token, y se firma utilizando la clave secreta. El token JWT generado se
    // devuelve como una cadena de caracteres.

    @Override
    public Date extractExpiration(String token, String secretKey) {
        Claims claims = extractAllClaims(token, secretKey);
        return claims.getExpiration();
    }

    // Este método utiliza el método extractAllClaims para obtener todas las
    // reclamaciones del token JWT. Luego, extrae y devuelve la fecha de expiración del
    // token utilizando el método getExpiration() del objeto Claims. La clave secreta
    // (secretKey) se utiliza para verificar la firma del token y asegurar su integridad
    // antes de extraer las reclamaciones.

}
