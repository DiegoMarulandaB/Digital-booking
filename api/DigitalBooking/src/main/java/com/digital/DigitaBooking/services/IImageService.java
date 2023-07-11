package com.digital.DigitaBooking.services;


import com.digital.DigitaBooking.models.dtos.ImageDTO;
import com.digital.DigitaBooking.models.entities.Tour;

import java.util.Set;

public interface IImageService {

    void saveImage(ImageDTO imageDTO, Tour newTour);

    ImageDTO getImage(Long id);

    void updateImage(Long id, ImageDTO imageDTO);

    void deleteImage(Long id);

    Set<ImageDTO> getImages();
}
