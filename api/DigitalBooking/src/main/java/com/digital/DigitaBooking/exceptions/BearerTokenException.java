package com.digital.DigitaBooking.exceptions;

public class BearerTokenException extends RuntimeException {

    public BearerTokenException(String message) {
        super(message);
    }
}

// Esta excepción específica se utiliza para representar un problema relacionado con el
// token de autorización "Bearer".
// Un token de autorización "Bearer" es un tipo de token utilizado en las solicitudes HTTP
// para autenticar y autorizar el acceso a recursos protegidos. El cliente lleva consigo
// este token en las solicitudes y el servidor lo utiliza para verificar la identidad y los
// permisos del cliente.
// Volviendo con el tema, la clase BearerTokenException, representa una excepción no
// comprobada que se utiliza para indicar un problema relacionado con el token de
// autorización "Bearer".
// Puede contener un mensaje descriptivo para ayudar a identificar la causa de la excepción.