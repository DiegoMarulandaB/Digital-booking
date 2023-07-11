package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageCategoryDTO {

    private Integer id;
    private String imageTitle;
    private String imageUrl;
    private Category category;

}
