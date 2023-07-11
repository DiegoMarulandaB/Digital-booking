package com.digital.DigitaBooking.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class LogoutController {

    @PostMapping(path = "/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {

        // Invalidar la sesión actual
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Eliminar las cookies específicas (si es necesario)
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        // Redireccionar a la página de inicio de sesión
        return "redirect:/login";
    }

}

// El controlador LogoutController contiene el método logOut anotado con @PostMapping para manejar la
// solicitud HTTP POST en la ruta /logout. Dentro de este método, se invalida la sesión actual llamando a
// request.getSession(false) y se eliminan las cookies específicas llamando a request.getCookies(). Luego, se
// establece el tiempo de vida de la cookie en 0 usando cookie.setMaxAge(0) y se agrega la cookie actualizada
// a la respuesta usando response.addCookie(cookie). Por último, se retorna un mensaje de redirección
// "redirect:/login" para redirigir al usuario a la página de inicio de sesión.
