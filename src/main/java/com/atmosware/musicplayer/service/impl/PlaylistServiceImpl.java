package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.PlaylistConverter;
import com.atmosware.musicplayer.converter.SongConverter;
import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Playlist;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.PlaylistRepository;
import com.atmosware.musicplayer.service.PlaylistService;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import com.atmosware.musicplayer.util.security.AuthenticationFacadeImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository repository;
    private final SongService songService;
    private final PlaylistConverter converter;
    private final UserService userService;
    private final SongConverter songConverter;
    private final AuthenticationFacade authenticationFacade;
    @Override
    public void create(PlaylistRequest request) {
        String email = authenticationFacade.getUsername();
        User user = userService.findByEmail(email);
        Playlist playlist = converter.convertToEntity(request);
        playlist.setId(0L);
        playlist.setCreatedDate(LocalDateTime.now());
        playlist.setUser(user);
        repository.save(playlist);
    }

    @Override
    public void createSongToPlaylist(Long songId, Long playlistId) {
        SongResponse songResponse = songService.getById(songId);
        Song song = songConverter.convertToEntity(songResponse);
        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().add(song);
        repository.save(playlist);
    }

    @Override
    public void update(PlaylistRequest request, Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        playlist.setUpdatedDate(LocalDateTime.now());
        playlist.setName(request.getName());
        repository.save(playlist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteSongToPlaylist(Long songId, Long playlistId) {
        SongResponse songResponse = songService.getById(songId);
        Song song = songConverter.convertToEntity(songResponse);
        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().remove(song);
        repository.save(playlist);
    }

    @Override
    public PlaylistResponse getById(Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        Set<SongResponse> songResponses = songConverter.convertToResponseList(playlist.getSongs());
        PlaylistResponse playlistResponse = converter.convertToResponse(playlist);
        playlistResponse.setSongs(songResponses);
        return playlistResponse;
    }

    @Override
    public List<PlaylistResponse> getAll() {
        List<Playlist> playlists = repository.findAll();
        return playlists.stream()
                .map(playlist -> {
                    Set<SongResponse> songResponses = songConverter.convertToResponseList(playlist.getSongs());
                    PlaylistResponse playlistResponse = converter.convertToResponse(playlist);
                    playlistResponse.setSongs(songResponses);
                    return playlistResponse;
                })
                .toList();
    }
}
