package com.digital.DigitaBooking.converters;

import com.digital.DigitaBooking.models.dtos.UserDTO;
import com.digital.DigitaBooking.models.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDTOConverter implements Converter<User, UserDTO> {

    //  Esta clase se encarga de convertir un objeto User en un objeto UserDTO utilizando
    //  la lógica definida en el método convert().

    @Override
    public UserDTO convert(User source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setUserName(source.getUsername());
        userDTO.setUserFirstName(source.getUserFirstName());
        userDTO.setUserLastName(source.getUserLastName());
        userDTO.setLatitude(source.getLatitude());
        userDTO.setLongitude(source.getLongitude());
        userDTO.setRole(source.getRole().name());
        return userDTO;

    }

    // Se establece el nombre de usuario (source.getUsername()) en la propiedad userName
    // del objeto UserDTO, y se establece el nombre del rol (source.getRole().name()) en
    // la propiedad role.

}

