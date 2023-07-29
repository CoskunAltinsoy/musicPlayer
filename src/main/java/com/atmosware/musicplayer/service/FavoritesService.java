package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;

import java.util.List;

public interface FavoritesService {
    void createSongToFavorite(Long songId, Long favoriteId);
    void deleteSongToFavorite(Long songId, Long favoriteId);
    FavoriteResponse getById(Long id);
    List<FavoriteResponse> getAll();
}
