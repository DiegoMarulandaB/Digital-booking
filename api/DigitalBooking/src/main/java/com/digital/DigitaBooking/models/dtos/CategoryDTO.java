package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.ImageCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDTO {

    private Integer id;
    private String categoryName;
    private String categoryDescription;
    private ImageCategory imageCategory;

}
