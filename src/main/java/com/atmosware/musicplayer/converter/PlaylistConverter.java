package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.model.entity.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistConverter {
    public Playlist convertToEntity(PlaylistRequest request) {
        return Playlist.builder()
                .name(request.getName())
                .build();
    }
    public PlaylistResponse convertToResponse(Playlist playlist) {
        return PlaylistResponse.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .build();
    }
}
