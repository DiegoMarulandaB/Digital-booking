package com.digital.DigitaBooking.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface IBearerTokenResolver {

    String resolve(HttpServletRequest request);

}

// Este método acepta un parámetro de tipo HttpServletRequest llamado request, que representa
// la solicitud HTTP de la cual se desea obtener el token de autorización.
// La implementación de esta interfaz será responsable de proporcionar la lógica específica
// para resolver y extraer el token de autorización "Bearer" de la solicitud.
// La cadena de retorno del método resolve representa el token de autorización "Bearer"
// obtenido de la solicitud HTTP.