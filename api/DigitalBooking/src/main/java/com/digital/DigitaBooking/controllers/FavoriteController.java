package com.digital.DigitaBooking.controllers;

import com.digital.DigitaBooking.models.dtos.FavoriteDTO;
import com.digital.DigitaBooking.services.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private IFavoriteService favoriteService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        favoriteService.saveFavorite(favoriteDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public FavoriteDTO getFavorite(@PathVariable Long id) {

        return favoriteService.getFavorite(id);
    }

    @GetMapping
    public Collection<FavoriteDTO> getFavorites() {

        return favoriteService.getFavorites();
    }


    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
