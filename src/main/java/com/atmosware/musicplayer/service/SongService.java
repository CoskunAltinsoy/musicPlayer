package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;

import java.util.List;

public interface SongService {
    void create(SongRequest request);
    void update(SongRequest request, Long id);
    void delete(Long id);
    SongResponse getById(Long id);
    List<SongResponse> getAll();
    List<SongResponse> getAllByAlbum(Long albumId);
    Song findById(Long id);
}
