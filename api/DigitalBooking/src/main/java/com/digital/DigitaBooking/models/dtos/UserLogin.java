package com.digital.DigitaBooking.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin {

    @NotEmpty
    @Length(min = 15, max = 30)
    private String userName;

    @NotEmpty(message = "Password field cannot be empty")
    @Length(min = 8,max = 20)
    private String password;
}

// Esta clase se utiliza para representar los datos de inicio de sesión de un usuario.
// Con las anotaciones @NotEmpty y @Length aplicamos validaciones en los campos y aseguramos
// que se proporcionen valores válidos durante el inicio de sesión.