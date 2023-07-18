package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;

import java.util.List;

public interface PlaylistService {
    void create(PlaylistRequest request);

    void createSongToPlaylist(Long songId, Long playlistId);

    void update(PlaylistRequest request, Long id);

    void delete(Long id);
    void deleteSongToPlaylist(Long songId, Long playlistId);

    PlaylistResponse getById(Long id);

    List<PlaylistResponse> getAll();
}
