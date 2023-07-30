package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface FavoritesService {
    Result createSongToFavorite(Long songId, Long favoriteId);
    Result deleteSongToFavorite(Long songId, Long favoriteId);
    DataResult<FavoriteResponse> getById(Long id);
    DataResult<List<FavoriteResponse>> getAll();
}
