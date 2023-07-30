package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface ArtistService {
    Result create(ArtistRequest request);

    Result update(ArtistRequest request, Long id);

    Result delete(Long id);

    DataResult<ArtistResponse> getById(Long id);

    DataResult<List<ArtistResponse>> getAll();

    DataResult<ArtistResponse> getByName(String name);

    Artist findById(Long id);
}
