package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistConverter {
    public Artist convertToEntity(ArtistRequest request) {

        return Artist.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
    public Artist convertToEntity(ArtistResponse response) {

        return Artist.builder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .build();
    }
    public Artist convertToEntity(Artist artist) {

        return Artist.builder()
                .id(artist.getId())
                .createdDate(artist.getCreatedDate())
                .updatedDate(artist.getUpdatedDate())
                .name(artist.getName())
                .description(artist.getDescription())
                .isVerified(artist.isVerified())
                .numberOfAlbum(artist.getNumberOfAlbum())
                .numberOfSongs(artist.getNumberOfSongs())
                .albums(artist.getAlbums())
                .songs(artist.getSongs())
                .favorites(artist.getFavorites())
                .build();
    }
    public ArtistResponse convertToResponse(Artist artist) {

        return ArtistResponse.builder()
                .id(artist.getId())
                .name(artist.getName())
                .description(artist.getDescription())
                .build();
    }
    public ArtistRequest convertToRequest(Artist artist) {

        return ArtistRequest.builder()
                .name(artist.getName())
                .description(artist.getDescription())
                .build();
    }
}