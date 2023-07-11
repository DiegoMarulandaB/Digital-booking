package com.digital.DigitaBooking.services.impl;

import com.amazonaws.http.HttpResponse;
import com.digital.DigitaBooking.converters.CategoryToCategoryDTOConverter;
import com.digital.DigitaBooking.exceptions.BadRequestException;
import com.digital.DigitaBooking.models.dtos.Response;
import com.digital.DigitaBooking.models.entities.Category;
import com.digital.DigitaBooking.models.dtos.CategoryDTO;
import com.digital.DigitaBooking.models.entities.ImageCategory;
import com.digital.DigitaBooking.repositories.ICategoryRepository;
import com.digital.DigitaBooking.services.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import javax.swing.text.html.parser.Entity;
import java.util.*;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CategoryToCategoryDTOConverter categoryConverter;

    @Override
    public Category saveCategory(CategoryDTO categoryDTO) {
        Category category = mapper.convertValue(categoryDTO, Category.class);
        Category newCategory = categoryRepository.save(category);
        return newCategory;
    }

    @Override
    public CategoryDTO getCategory(Integer id) {

        Category category = categoryRepository.findById(id).get();
        CategoryDTO categoryDTO = null;
        categoryDTO = categoryConverter.convert(category);
        return categoryDTO;
    }

    @Override
    public void updateCategory(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(id).map(category -> {
            category.setCategoryName(categoryDTO.getCategoryName());
            category.setCategoryDescription(categoryDTO.getCategoryDescription());
            return categoryRepository.save(category);
        });

    }

    @Override
    public void deleteCategory(Integer id) {

        categoryRepository.deleteById(id);
    }

    @Override
    public Set<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        Set<CategoryDTO> categoriesDTO = new HashSet<>();
        for (Category category :
                categories) {
            categoriesDTO.add(categoryConverter.convert(category));

        }
        return categoriesDTO;
    }

}
