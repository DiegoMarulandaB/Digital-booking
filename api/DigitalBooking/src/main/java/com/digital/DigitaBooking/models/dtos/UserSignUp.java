package com.digital.DigitaBooking.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUp {

    // La clase "UserSignUp" es un objeto de transferencia de datos utilizado para
    // representar los datos de registro de usuarios, incluyendo un nombre de usuario y una
    // contraseña.

    @NotEmpty
    @Length(min = 8, max = 20)
    private String userName;

    @NotEmpty
    @Length(min = 4, max = 20)
    private String password;

    @NotEmpty
    @Length(min = 4, max = 20)
    private String userFirstName;

    @NotEmpty
    @Length(min = 4, max = 20)
    private String userLastName;

    @DecimalMax("90.00")
    @DecimalMin("-90.00")
    private Double latitude;

    @DecimalMax("108.00")
    @DecimalMin("-180.00")
    private Double longitude;





}
