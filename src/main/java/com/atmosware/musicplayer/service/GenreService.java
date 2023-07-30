package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface GenreService {
    Result create(GenreRequest request);

    Result update(GenreRequest request, Long id);

    Result delete(Long id);

    DataResult<GenreResponse> getById(Long id);

    DataResult<List<GenreResponse>> getAll();
    Genre findById(Long id);
}
