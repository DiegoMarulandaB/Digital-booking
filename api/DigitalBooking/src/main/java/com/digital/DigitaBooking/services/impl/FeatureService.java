package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.models.dtos.FeatureDTO;
import com.digital.DigitaBooking.models.entities.Feature;
import com.digital.DigitaBooking.repositories.IFeatureRepository;
import com.digital.DigitaBooking.services.IFeatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FeatureService implements IFeatureService {

    @Autowired
    private IFeatureRepository featureRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void saveFeature(FeatureDTO featureDTO) {
        Feature feature = mapper.convertValue(featureDTO, Feature.class);
        featureRepository.save(feature);

    }

    @Override
    public FeatureDTO getFeature(Long id) {
        Optional<Feature> feature = featureRepository.findById(id);
        FeatureDTO featureDTO = null;
        if (feature.isPresent())
            featureDTO = mapper.convertValue(feature, FeatureDTO.class);

        return featureDTO;
    }

    @Override
    public void updateFeature(Long id, FeatureDTO featureDTO) {
        Optional<Feature> optionalFeature = featureRepository.findById(id).map(feature -> {
            feature.setFeatureName(featureDTO.getFeatureName());
            return featureRepository.save(feature);
        });
    }

    @Override
    public void deleteFeature(Long id) {
        featureRepository.deleteById(id);
    }

    @Override
    public Set<FeatureDTO> getFeatures() {
        List<Feature> features = featureRepository.findAll();
        Set<FeatureDTO> featuresDTO = new HashSet<>();
        for (Feature feature :
                features) {
            featuresDTO.add(mapper.convertValue(feature, FeatureDTO.class));

        }
        return featuresDTO;
    }
}
