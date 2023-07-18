package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AlbumConverter{

    public Album convertToEntity(AlbumRequest request) {

        return Album.builder()
                .artist(new Artist()).id(request.getArtistId())
                .name(request.getName())
                .releasedYear(request.getReleasedYear())
                .build();
    }
    public Album convertToEntity(AlbumResponse response) {

        return Album.builder()
                .id(response.getId())
                .artist(new Artist()).id(response.getArtistId())
                .name(response.getName())
                .releasedYear(response.getReleasedYear())
                .build();
    }

    public Album convertToEntity(Album album) {

        return Album.builder()
                .id(album.getId())
                .createdDate(album.getCreatedDate())
                .updatedDate(album.getUpdatedDate())
                .name(album.getName())
                .releasedYear(album.getReleasedYear())
                .songs(album.getSongs())
                .artist(album.getArtist())
                .favorites(album.getFavorites())
                .build();
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
