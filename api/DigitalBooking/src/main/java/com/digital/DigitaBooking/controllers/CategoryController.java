package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.*;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.entities.ImageCategory;
import com.digital.DigitaBooking.models.entities.Tour;
import com.digital.DigitaBooking.service.impl.AWSS3ServiceImpl;
import com.digital.DigitaBooking.services.impl.CategoryService;
import com.digital.DigitaBooking.services.impl.ImageCategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AWSS3ServiceImpl awss3Service;

    @Autowired
    private ImageCategoryService imageCategoryService;

    private ObjectMapper mapper = new ObjectMapper();


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> loadImage(@RequestPart(value = "file") MultipartFile image,
                                                @RequestPart(value = "Category") String categoryString) throws IOException {
        ImageCategoryDTO imageCategoryDTO = new ImageCategoryDTO();
        CategoryDTO categoryDTO = mapper.readValue(categoryString, CategoryDTO.class);

        try {
            File mainFile = new File(image.getOriginalFilename());
            String newFileName = System.currentTimeMillis() + "_" + mainFile.getName();
            awss3Service.uploadFile(image);
            imageCategoryDTO.setImageTitle(mainFile.getName());
            imageCategoryDTO.setImageUrl(awss3Service.generateUrl(newFileName).replaceFirst("/[0-9]+_", "/_"));
            Category newCategory = categoryService.saveCategory(categoryDTO);
            imageCategoryService.saveImageCategory(imageCategoryDTO, newCategory);
            mainFile.delete();

        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public Response getCategory(@PathVariable Integer id) {
        try {
            CategoryDTO categoryDTO = categoryService.getCategory(id);
            Response response = new Response(categoryDTO, HttpStatus.OK);
            return response;

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            JsonObject jsonReponse = new JsonObject();
            jsonReponse.addProperty("status", 404);
            jsonReponse.addProperty("error", e.getMessage());
            return new Response(jsonReponse.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Collection<CategoryDTO> getCategories() {

        return categoryService.getCategories();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}