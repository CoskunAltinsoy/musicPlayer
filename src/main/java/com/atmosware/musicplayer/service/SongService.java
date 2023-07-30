package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface SongService {
    Result create(SongRequest request);
    Result update(SongRequest request, Long id);
    Result delete(Long id);
    DataResult<SongResponse> getById(Long id);
    DataResult<List<SongResponse>> getAll();
    DataResult<List<SongResponse>> getAllByAlbum(Long albumId);
    Song findById(Long id);
}
