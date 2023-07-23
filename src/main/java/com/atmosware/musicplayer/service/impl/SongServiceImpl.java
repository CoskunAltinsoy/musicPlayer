package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.GenreConverter;
import com.atmosware.musicplayer.converter.SongConverter;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.repository.SongRepository;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.service.SongService;
import com.fasterxml.jackson.core.base.GeneratorBase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final AlbumService albumService;
    private final GenreService genreService;
    private final SongConverter converter;
    private final GenreConverter genreConverter;

    @Override
    public void create(SongRequest request) {
        Album album = albumService.findById(request.getAlbumId());
        Set<Genre> genres = setGenreIds(request);
        Song song = converter.convertToEntity(request);
        song.setId(0L);
        song.setAlbum(album);
        song.setGenres(genres);
        song.setCreatedDate(LocalDateTime.now());
        repository.save(song);
    }

    @Override
    public void update(SongRequest request, Long id) {
        Album album = albumService.findById(request.getAlbumId());
        Set<Genre> genres = setGenreIds(request);
        Song song = repository.findById(id).orElseThrow();
        song.setName(request.getName());
        song.setAlbum(album);
        song.setGenres(genres);
        song.setUpdatedDate(LocalDateTime.now());
        repository.save(song);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SongResponse getById(Long id) {
        Song song = repository.findById(id).orElseThrow();
        AlbumResponse albumResponse = albumService.getById(song.getAlbum().getId());
        Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
        SongResponse songResponse = converter.convertToResponse(song);
        songResponse.setAlbum(albumResponse);
        songResponse.setGenres(genreResponses);
        return songResponse;
    }

    @Override
    public List<SongResponse> getAll() {
        List<Song> songs = repository.findAll();
        return songs
                .stream()
                .map(song -> {
                    AlbumResponse albumResponse = albumService.getById(song.getAlbum().getId());
                    Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
                    SongResponse songResponse = converter.convertToResponse(song);
                    songResponse.setAlbum(albumResponse);
                    songResponse.setGenres(genreResponses);
                    return songResponse;
                })
                .toList();
    }

    @Override
    public List<SongResponse> getAllByAlbum(Long albumId) {
        List<Song> songs = repository.findByAlbum_Id(albumId);
        return songs
                .stream()
                .map(song -> {
                    AlbumResponse albumResponse = albumService.getById(song.getAlbum().getId());
                    Set<GenreResponse> genreResponses = genreConverter.convertToResponseList(song.getGenres());
                    SongResponse songResponse = converter.convertToResponse(song);
                    songResponse.setAlbum(albumResponse);
                    songResponse.setGenres(genreResponses);
                    return songResponse;
                })
                .toList();
    }
    private Set<Genre> setGenreIds(SongRequest request){
        return request.getGenreIds().stream()
                .map(genreId -> {
                    return genreService.findById(genreId);
                })
                .collect(Collectors.toSet());
    }
}
