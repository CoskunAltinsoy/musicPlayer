package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public Genre convertToEntity(GenreRequest request) {

        return Genre.builder()
                .name(request.getName())
                .build();
    }
    public Genre convertToEntity(GenreResponse response) {

        return Genre.builder()
                .name(response.getName())
                .build();
    }
    public Genre convertToEntity(Genre genre) {

        Genre newGenre;
        newGenre = genre;
        return newGenre;
    }
    public GenreResponse convertToResponse(Genre genre) {

        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
    public GenreRequest convertToRequest(Genre genre) {

        return GenreRequest.builder()
                .name(genre.getName())
                .build();
    }
}
