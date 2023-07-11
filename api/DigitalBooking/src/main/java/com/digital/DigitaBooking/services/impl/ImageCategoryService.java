package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.models.dtos.ImageCategoryDTO;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.ImageCategory;
import com.digital.DigitaBooking.repositories.ICategoryRepository;
import com.digital.DigitaBooking.repositories.IImageCategoryRepository;
import com.digital.DigitaBooking.services.IImageCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageCategoryService implements IImageCategoryService {

    @Autowired
    private IImageCategoryRepository imageCategoryRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public ImageCategory saveImageCategory(ImageCategoryDTO imageCategoryDTO, Category category) {
        ImageCategory imageCategory = mapper.convertValue(imageCategoryDTO, ImageCategory.class);
        imageCategory.setCategory(category);
        ImageCategory newimageCategory =imageCategoryRepository.save(imageCategory);
        return newimageCategory;
    }

    @Override
    public ImageCategoryDTO getImageCategory(Integer id) {
        Optional<ImageCategory> imageCategory = imageCategoryRepository.findById(id);
        ImageCategoryDTO imageCategoryDTO = null;
        if (imageCategory.isPresent())
            imageCategoryDTO = mapper.convertValue(imageCategory, ImageCategoryDTO.class);

        return imageCategoryDTO;
    }

    @Override
    public void updateImageCategory(Integer id, ImageCategoryDTO imageCategoryDTO) {
        Optional<ImageCategory> optionalImageCategory = imageCategoryRepository.findById(id).map(imageCategory -> {
            imageCategory.setImageTitle(imageCategoryDTO.getImageTitle());
            imageCategory.setImageUrl(imageCategoryDTO.getImageUrl());
            return imageCategoryRepository.save(imageCategory);
        });

    }

    @Override
    public void deleteImageCategory(Integer id) {

        imageCategoryRepository.deleteById(id);
    }

    @Override
    public Set<ImageCategoryDTO> getImagesCategory() {
        List<ImageCategory> imagesCategory = imageCategoryRepository.findAll();
        Set<ImageCategoryDTO> imagesCategoryDTO = new HashSet<>();
        for (ImageCategory imageCategory :
                imagesCategory) {
            imagesCategoryDTO.add(mapper.convertValue(imageCategory, ImageCategoryDTO.class));

        }
        return imagesCategoryDTO;
    }
}
