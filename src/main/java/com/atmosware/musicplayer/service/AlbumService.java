package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AlbumService {
    void create(AlbumRequest request);

    void update(AlbumRequest request, Long id);

    void delete(Long id);

    AlbumResponse getById(Long id);

    List<AlbumResponse> getAll();

    AlbumResponse getByName(String name);

    List<AlbumResponse> findByReleasedYearGreaterThan(LocalDateTime year);
}
