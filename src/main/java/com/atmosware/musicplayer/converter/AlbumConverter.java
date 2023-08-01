package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    public Album convertToEntity(AlbumRequest request) {
        return Album.builder()
                .name(request.getName())
                .releasedYear(request.getReleasedYear())
                .build();
    }

    public Album convertToEntity(AlbumResponse response) {
        return Album.builder()
                .name(response.getName())
                .releasedYear(response.getReleasedYear())
                .build();
    }

    public AlbumResponse convertToResponse(Album album) {

        return AlbumResponse.builder()
                .id(album.getId())
                .name(album.getName())
                .releasedYear(album.getReleasedYear())
                .build();
    }
}
