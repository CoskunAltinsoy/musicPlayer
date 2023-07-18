package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.repository.SongRepository;
import com.atmosware.musicplayer.service.SongService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final ModelMapper mapper;

    @Override
    public void create(SongRequest request) {
        Song song = mapper.map(request, Song.class);
        song.setId(0L);
        song.setCreatedDate(LocalDateTime.now());
        repository.save(song);
    }

    @Override
    public void update(SongRequest request, Long id) {
        Song song = repository.findById(id).orElseThrow();
        song.setUpdatedDate(LocalDateTime.now());
        Song updatedSong = mapper.map(request, Song.class);
        repository.save(updatedSong);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SongResponse getById(Long id) {
        Song song = repository.findById(id).orElseThrow();
        SongResponse response = mapper.map(song, SongResponse.class);
        return response;
    }

    @Override
    public List<SongResponse> getAll() {
        List<Song> songs = repository.findAll();
        List<SongResponse> responses = songs
                .stream()
                .map(song -> mapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<SongResponse> getAllByAlbum(Long albumId) {
        List<Song> songs = repository.findByAlbum_Id(albumId);
        List<SongResponse> responses = songs
                .stream()
                .map(song -> mapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public List<SongResponse> getAllByArtist(Long artistId) {
        List<Song> songs = repository.findByArtist_Id(artistId);
        List<SongResponse> responses = songs
                .stream()
                .map(song -> mapper.map(song, SongResponse.class))
                .collect(Collectors.toList());
        return responses;
    }
}
