package com.digital.DigitaBooking.services;

import com.digital.DigitaBooking.models.dtos.FavoriteDTO;

import java.util.Set;

public interface IFavoriteService {

    void saveFavorite(FavoriteDTO favoriteDTO);

    FavoriteDTO getFavorite(Long id);

    void deleteFavorite(Long id);

    Set<FavoriteDTO> getFavorites();

}
