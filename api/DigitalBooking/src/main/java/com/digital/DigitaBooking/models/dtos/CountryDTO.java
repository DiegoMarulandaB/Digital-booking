package com.digital.DigitaBooking.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.digital.DigitaBooking.models.entities.Tour;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDTO {

    private Integer id;
    private String countryName;
    private String capitalName;
    private Double latitude;
    private Double longitude;

}
