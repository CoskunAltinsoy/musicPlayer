package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import org.springframework.stereotype.Component;

@Component
public class SongConverter {
    public Song convertToEntity(SongRequest request) {

        Song song = new Song();
        song.getAlbum().setId(request.getAlbumId());
        song.getArtist().setId(request.getArtistId());
        song.getGenres().stream()
                .forEach(genre -> request.getGenreIds().stream().forEach(id -> genre.setId(id)));
        song.setName(request.getName());
        return song;
    }

    public Song convertToEntity(SongResponse response) {

        Song song = new Song();
        song.getAlbum().setId(response.getAlbumId());
        song.getArtist().setId(response.getArtistId());
        song.getGenres().stream()
                .forEach(genre -> response.getGenreIds().stream().forEach(id -> genre.setId(id)));
        song.setName(response.getName());
        return song;
    }

    public SongResponse convertToResponse(Song song) {
        return SongResponse.builder()
                .id(song.getId())
                .name(song.getName())
                .build();
    }

    public SongResponse convertToRequest(Song song) {
        return SongResponse.builder()
                .name(song.getName())
                .build();
    }
}
