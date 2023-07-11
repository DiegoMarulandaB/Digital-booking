package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.AWSS3Service;
import com.digital.DigitaBooking.models.dtos.ImageDTO;
import com.digital.DigitaBooking.models.dtos.ImageLoaderDTO;
import com.digital.DigitaBooking.service.impl.AWSS3ServiceImpl;
import com.digital.DigitaBooking.services.impl.ImageService;


import jakarta.persistence.SecondaryTable;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveImage(@RequestBody ImageDTO imageDTO) {

//        imageService.saveImage(imageDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ImageDTO getImage(@PathVariable Long id) {

        return imageService.getImage(id);
    }

    @GetMapping
    public Collection<ImageDTO> getImages() {

        return imageService.getImages();
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateImage(@PathVariable Long id, @RequestBody ImageDTO imageDTO) {

        imageService.updateImage(id, imageDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteImage(@PathVariable Long id) {

        imageService.deleteImage(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
