package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.score.Counter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CounterDTO {

    private Long id;
    private Long tourId;
    private Double average;
    private int oneStar;
    private int twoStars;
    private int treeStars;
    private int fourStars;
    private int fiveStars;

    public CounterDTO(Counter counter) {
        this.id = counter.getId();
        this.tourId = counter.getTour().getId();
        this.average = counter.getAverage();
        this.oneStar = counter.getOneStar();
        this.twoStars = counter.getTwoStars();
        this.treeStars = counter.getTreeStars();
        this.fourStars = counter.getFourStars();
        this.fiveStars = counter.getFiveStars();
    }
}
