package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.service.SongService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SongServiceImplTest {
    @Autowired
    private SongService songService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ModelMapper mapper;

    @Test
    public void create(){
        AlbumResponse albumResponse = albumService.getById(1L);
        Album album = mapper.map(albumResponse, Album.class);

        ArtistResponse artistResponse = artistService.getById(1L);
        Artist artist = mapper.map(artistResponse,Artist.class);

        GenreResponse genreResponse = genreService.getById(1L);
        GenreResponse genreSecondResponse = genreService.getById(2L);
        Set<Genre> genres =new HashSet<>();
        Genre genre = mapper.map(genreResponse, Genre.class);
        Genre secondGenre = mapper.map(genreSecondResponse, Genre.class);
        genres.add(genre);
        genres.add(secondGenre);

        Song song = new Song();
        song.setName("Adımız Miskindir Bizim");
        song.setAlbum(album);
        song.setArtist(artist);
        song.setGenres(genres);

        SongRequest request = mapper.map(song, SongRequest.class);
        songService.create(request);
    }
}