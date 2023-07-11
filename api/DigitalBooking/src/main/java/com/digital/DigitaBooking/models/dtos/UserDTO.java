package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.entities.User;
import com.digital.DigitaBooking.models.entities.score.Score;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String password;
    private String role;
    private Double latitude;
    private Double longitude;
    private List<ScoreDTO> scoreList;


    public UserDTO(User user) throws BadRequestException {
        if (user != null) {
            this.id = user.getId();
            this.userName = user.getUsername();
            this.userFirstName = user.getUserFirstName();
            this.userLastName = user.getUserLastName();
            this.scoreList = convertScoreListToDTO(user.getScores());
        } else {
            throw new BadRequestException("El usuario no existe");
        }
    }

    public UserDTO(Long id, String userName, String userFirstName, String userLastName, String password) {
        this.id = id;
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.password = password;
    }

    private List<ScoreDTO> convertScoreListToDTO(List<Score> scores) {

        return scores.stream().map(ScoreDTO::new).collect(Collectors.toList());
    }

    // El código convierte una lista de objetos Score en una lista de objetos ScoreDTO.
}