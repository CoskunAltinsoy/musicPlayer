package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface PlaylistService {
    Result create(PlaylistRequest request);

    Result createSongToPlaylist(Long songId, Long playlistId);

    Result update(PlaylistRequest request, Long id);

    Result delete(Long id);
    Result deleteSongToPlaylist(Long songId, Long playlistId);

    DataResult<PlaylistResponse> getById(Long id);

    DataResult<List<PlaylistResponse>> getAll();
}
