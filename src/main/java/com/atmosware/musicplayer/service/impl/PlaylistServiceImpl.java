package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Playlist;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.repository.PlaylistRepository;
import com.atmosware.musicplayer.service.PlaylistService;
import com.atmosware.musicplayer.service.SongService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository repository;
    private final SongService songService;
    private final ModelMapper mapper;

    @Override
    public void create(PlaylistRequest request) {
        Playlist playlist = mapper.map(request, Playlist.class);
        playlist.setId(0L);
        playlist.setCreatedDate(LocalDateTime.now());
        repository.save(playlist);
    }

    @Override
    public void createSongToPlaylist(Long songId, Long playlistId) {
        SongResponse songResponse = songService.getById(songId);
        Song song = mapper.map(songResponse, Song.class);

        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().add(song);
        repository.save(playlist);
    }

    @Override
    public void update(PlaylistRequest request, Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        playlist.setUpdatedDate(LocalDateTime.now());
        Playlist updatedPlaylist = mapper.map(request, Playlist.class);
        repository.save(updatedPlaylist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteSongToPlaylist(Long songId, Long playlistId) {
        SongResponse songResponse = songService.getById(songId);
        Song song = mapper.map(songResponse, Song.class);

        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().remove(song);
        repository.save(playlist);
    }

    @Override
    public PlaylistResponse getById(Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        PlaylistResponse response = mapper.map(playlist, PlaylistResponse.class);
        return response;
    }

    @Override
    public List<PlaylistResponse> getAll() {
        List<Playlist> playlists = repository.findAll();
        List<PlaylistResponse> responses = playlists
                .stream()
                .map(playlist -> mapper.map(playlist, PlaylistResponse.class))
                .collect(Collectors.toList());
        return responses;
    }
}
