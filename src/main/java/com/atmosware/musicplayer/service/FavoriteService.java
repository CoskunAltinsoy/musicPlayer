package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.FavoriteRequest;
import com.atmosware.musicplayer.dto.response.FavoriteResponse;

import java.util.List;

public interface FavoriteService {
    void create(FavoriteRequest request);

    void update(FavoriteRequest request, Long id);

    void delete(Long id);

    FavoriteResponse getById(Long id);

    List<FavoriteResponse> getAll();
}
