package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.models.dtos.ImageCategoryDTO;
import com.digital.DigitaBooking.services.impl.ImageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/image-category")
public class ImageCategoryController {

    @Autowired
    private ImageCategoryService imageCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveImageCategory(@RequestBody ImageCategoryDTO imageCategoryDTO) {

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ImageCategoryDTO getImageCategory(@PathVariable Integer id) {

        return imageCategoryService.getImageCategory(id);
    }

    @GetMapping
    public Collection<ImageCategoryDTO> getImagesCategory() {

        return imageCategoryService.getImagesCategory();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateImageCategory(@PathVariable Integer id, @RequestBody ImageCategoryDTO imageCategoryDTO) {

        imageCategoryService.updateImageCategory(id, imageCategoryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteImageCategory(@PathVariable Integer id) {

        imageCategoryService.deleteImageCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
