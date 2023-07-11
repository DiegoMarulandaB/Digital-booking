package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.models.dtos.ImageDTO;
import com.digital.DigitaBooking.models.entities.Image;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.repositories.IImageRepository;
import com.digital.DigitaBooking.services.IImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageService implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void saveImage(ImageDTO imageDTO, Tour newTour) {
        Image image = mapper.convertValue(imageDTO, Image.class);
        image.setTour(newTour);
        imageRepository.save(image);

    }

    @Override
    public ImageDTO getImage(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        ImageDTO imageDTO = null;
        if (image.isPresent())
            imageDTO = mapper.convertValue(image, ImageDTO.class);

        return imageDTO;
    }

    @Override
    public void updateImage(Long id, ImageDTO imageDTO) {
        Optional<Image> optionalImage = imageRepository.findById(id).map(image -> {
            image.setImageTitle(imageDTO.getImageTitle());
            image.setImageUrl(imageDTO.getImageUrl());
            return imageRepository.save(image);
        });
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Set<ImageDTO> getImages() {
        List<Image> images = imageRepository.findAll();
        Set<ImageDTO> imagesDTO = new HashSet<>();
        for (Image image :
                images) {
            imagesDTO.add(mapper.convertValue(image, ImageDTO.class));

        }
        return imagesDTO;
    }
}
