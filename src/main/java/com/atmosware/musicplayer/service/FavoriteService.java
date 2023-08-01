package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface FavoriteService {
    Result createSongToFavorite(Long songId);
    Result deleteSongToFavorite(Long songId, Long favoriteId);
    Result delete(Long favoriteId);
    DataResult<FavoriteResponse> getById(Long id);
    DataResult<List<FavoriteResponse>> getAll();
}
