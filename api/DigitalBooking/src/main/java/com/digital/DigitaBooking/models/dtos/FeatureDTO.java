package com.digital.DigitaBooking.models.dtos;

import com.digital.DigitaBooking.models.entities.Tour;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureDTO {

    private Long id;
    private String featureName;
    private Set<Tour> tour;
}
