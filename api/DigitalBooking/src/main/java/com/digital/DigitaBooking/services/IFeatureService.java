package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.models.dtos.FeatureDTO;
import com.digital.DigitaBooking.models.entities.Feature;

import java.util.Set;

public interface IFeatureService {

    void saveFeature(FeatureDTO featureDTO);

    FeatureDTO getFeature(Long id);

    void updateFeature(Long id, FeatureDTO featureDTO);

    void deleteFeature(Long id);

    Set<FeatureDTO> getFeatures();

}
