package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    public Album convertToEntity(AlbumRequest request) {
        Artist artist = new Artist();

        Album album = new Album();
        album.setArtist(artist);

        album.getArtist().setId(request.getArtistId());
        album.setName(request.getName());
        album.setReleasedYear(request.getReleasedYear());

        return album;
    }

    public Album convertToEntity(AlbumResponse response) {
        Artist artist = new Artist();

        Album album = new Album();
        album.setArtist(artist);

        album.getArtist().setId(response.getArtistId());
        album.setName(response.getName());
        album.setReleasedYear(response.getReleasedYear());

        return album;
    }

    public AlbumResponse convertToResponse(Album album) {

        return AlbumResponse.builder()
                .id(album.getId())
                .artistId(album.getArtist().getId())
                .name(album.getName())
                .releasedYear(album.getReleasedYear())
                .build();
    }

    public AlbumRequest convertToRequest(Album album) {

        return AlbumRequest.builder()
                .artistId(album.getArtist().getId())
                .name(album.getName())
                .releasedYear(album.getReleasedYear())
                .build();
    }
}
