package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import org.springframework.stereotype.Component;

import java.util.HashSet;

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
    public Song convertToEntity(Song song) {

        Song newSong;
        newSong = song;
        return newSong;
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
