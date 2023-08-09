package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.PlaylistConverter;
import com.atmosware.musicplayer.converter.SongConverter;
import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Playlist;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.PlaylistRepository;
import com.atmosware.musicplayer.service.PlaylistService;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    public Result create(PlaylistRequest request) {
        String email = authenticationFacade.getUsername();
        User user = userService.findByEmail(email);
        Playlist playlist = converter.convertToEntity(request);
        playlist.setId(0L);
        playlist.setCreatedDate(LocalDateTime.now());
        playlist.setUser(user);
        repository.save(playlist);
        return new Result(Message.Playlist.SUCCESSFUL);
    }

    @Override
    public Result createSongToPlaylist(Long songId, Long playlistId) {
        var songResponse = songService.getById(songId);
        Song song = songConverter.convertToEntity(songResponse.getData());
        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().add(song);
        repository.save(playlist);
        return new Result(Message.Playlist.SUCCESSFUL);
    }

    @Override
    public Result update(PlaylistRequest request, Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        playlist.setUpdatedDate(LocalDateTime.now());
        playlist.setName(request.getName());
        repository.save(playlist);
        return new Result(Message.Playlist.SUCCESSFUL);
    }

    @Override
    public Result delete(Long id) {
        checkIfPlaylistExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Playlist.SUCCESSFUL);
    }

    @Override
    public Result deleteSongToPlaylist(Long songId, Long playlistId) {
        var songResponse = songService.getById(songId);
        Song song = songConverter.convertToEntity(songResponse.getData());
        Playlist playlist = repository.findById(playlistId).orElseThrow();
        playlist.getSongs().remove(song);
        repository.save(playlist);
        return new Result(Message.Playlist.SUCCESSFUL);
    }

    @Override
    public DataResult<PlaylistResponse> getById(Long id) {
        Playlist playlist = repository.findById(id).orElseThrow();
        Set<SongResponse> songResponses = songConverter.convertToResponseList(playlist.getSongs());
        PlaylistResponse playlistResponse = converter.convertToResponse(playlist);
        playlistResponse.setSongs(songResponses);
        return new DataResult<PlaylistResponse>(Message.Playlist.SUCCESSFUL,playlistResponse);
    }

    @Override
    public DataResult<List<PlaylistResponse>> getAll() {
        List<Playlist> playlists = repository.findAll();
        var responses = playlists.stream()
                .map(playlist -> {
                    Set<SongResponse> songResponses = songConverter.convertToResponseList(playlist.getSongs());
                    PlaylistResponse playlistResponse = converter.convertToResponse(playlist);
                    playlistResponse.setSongs(songResponses);
                    return playlistResponse;
                })
                .toList();
        return new DataResult<List<PlaylistResponse>>(Message.Playlist.SUCCESSFUL,responses);
    }
    private void checkIfPlaylistExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Playlist.NOT_EXIST);
        }
    }
}
