package com.digital.DigitaBooking.models.dtos;

import java.util.List;

public class UserPageDTO extends PageResponseDTO<UserDTO> {
    public UserPageDTO(List<UserDTO> content) {
        super(content);
    }
}
// Cuando usamos UserPageDTO, se puede obtener una respuesta paginada de una lista de
// usuarios en forma de objetos UserDTO. Esta clase es útil cuando se desea paginar y enviar
// la información de usuarios en partes, permitiendo un acceso eficiente y controlado a
// través de la paginación.