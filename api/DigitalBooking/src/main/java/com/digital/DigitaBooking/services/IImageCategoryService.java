package com.digital.DigitaBooking.services;


import com.digital.DigitaBooking.models.dtos.ImageCategoryDTO;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.ImageCategory;

import java.util.Set;

public interface IImageCategoryService {

    ImageCategory saveImageCategory(ImageCategoryDTO imageCategoryDTO, Category category);

    ImageCategoryDTO getImageCategory(Integer id);

    void updateImageCategory(Integer id, ImageCategoryDTO imageCategoryDTO);

    void deleteImageCategory(Integer id);

    Set<ImageCategoryDTO> getImagesCategory();
}
