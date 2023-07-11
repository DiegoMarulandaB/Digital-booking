package com.digital.DigitaBooking.jwt;

import com.digital.DigitaBooking.exceptions.BearerTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class BearerTokenResolver implements IBearerTokenResolver {
// Esta clase es responsable de extraer y validar el token de autorización "Bearer" de
// una solicitud HTTP.
    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", 2);

    @Override
    public String resolve(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        } else {
            Matcher matcher = authorizationPattern.matcher(authorization);
            if (!matcher.matches()) {
                throw new BearerTokenException("The Bearer token is not properly structured.");
            } else {
                return matcher.group("token");
            }
        }
    }
}

// El método "resolve" toma una solicitud HTTP como parámetro y devuelve el token de
// autorización "Bearer" encontrado en la cabecera de la solicitud.
// Luego, se verifica si la cabecera comienza con la palabra "bearer" (ignorando
// mayúsculas y minúsculas). Si no es así, se devuelve null --> Esto se valida en el segundo
// parámetro donde se usa 2 como flags en la expresión regular indicando que la
// coincidencia del patrón es insensible a las diferencias de mayúsculas y minúsculas
// en el texto analizado.
// Si la cabecera comienza con "bearer", se utiliza una expresión regular para verificar
// si el formato del token es válido. Si no coincide con el patrón esperado, se lanza
// una excepción BearerTokenException.
// Si el token coincide con el patrón, se devuelve el token extraído del grupo "token"
// de la expresión regular.