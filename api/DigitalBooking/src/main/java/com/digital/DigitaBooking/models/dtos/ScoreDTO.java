package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.score.Score;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreDTO {

    private Long id;
    private Long scoreId;
    private Long userId;
    private String userName;
    private Integer value;

    public ScoreDTO(Score score) {
        this.id = score.getId();
        this.scoreId = score.getCounter().getId();
        this.userId = score.getUser().getId();
        this.userName = score.getUser().getUsername();
        this.value = score.getValue();
    }

}
