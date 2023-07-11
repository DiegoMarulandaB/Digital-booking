package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.Tour;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageDTO {

    private Long id;
    private String imageTitle;
    private String imageUrl;
    private Tour tour;

}
