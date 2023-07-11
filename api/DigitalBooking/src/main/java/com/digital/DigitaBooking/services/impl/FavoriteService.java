package com.digital.DigitaBooking.services.impl;

import com.digital.DigitaBooking.models.dtos.FavoriteDTO;
import com.digital.DigitaBooking.models.entities.Favorite;
import com.digital.DigitaBooking.repositories.IFavoriteRepository;
import com.digital.DigitaBooking.services.IFavoriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FavoriteService implements IFavoriteService {

    @Autowired
    private IFavoriteRepository favoriteRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void saveFavorite(FavoriteDTO favoriteDTO) {
        Favorite favorite = mapper.convertValue(favoriteDTO, Favorite.class);
        favoriteRepository.save(favorite);
    }

    @Override
    public FavoriteDTO getFavorite(Long id) {
        Optional<Favorite> favorite = favoriteRepository.findById(id);
        FavoriteDTO favoriteDTO = null;
        if (favorite.isPresent())
            favoriteDTO = mapper.convertValue(favorite, FavoriteDTO.class);

        return favoriteDTO;
    }

    @Override
    public void deleteFavorite(Long id) {

        favoriteRepository.deleteById(id);
    }

    @Override
    public Set<FavoriteDTO> getFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        Set<FavoriteDTO> favoritesDTO = new HashSet<>();
        for (Favorite favorite :
                favorites) {
            favoritesDTO.add(mapper.convertValue(favorite, FavoriteDTO.class));

        }
        return favoritesDTO;
    }
}
