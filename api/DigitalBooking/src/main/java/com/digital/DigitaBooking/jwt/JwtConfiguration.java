package com.digital.DigitaBooking.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record JwtConfiguration(@Value("${jwt.secret.key}")
                               String secretKey,
                               @Value("${jwt.secret.expiration.in.milliseconds}")
                               Long expiration) {
}

// Aquí hacemos uso del concepto de "record" para definir una clase inmutable con
// propiedades y métodos de acceso automáticos.
// * secretKey: Representa la clave secreta utilizada en el contexto de JSON Web Tokens.
// El valor de esta propiedad se obtiene de la configuración de la aplicación utilizando la
// anotación @Value y la expresión ${jwt.secret.key}. Su valor lo obtiene del archivo
// application.properties, al igual que la duración de expiración de los JWT en milisegundos,
// para nuestro caso es de 45 minutos.
