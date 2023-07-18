package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;

import java.util.List;

public interface GenreService {
    void create(GenreRequest request);

    void update(GenreRequest request, Long id);

    void delete(Long id);

    GenreResponse getById(Long id);

    List<GenreResponse> getAll();
}
