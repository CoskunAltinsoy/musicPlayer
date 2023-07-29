package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlists")
public class PlaylistsController {
    private final PlaylistService service;

    @PostMapping
    public void create(@RequestBody PlaylistRequest request){
        service.create(request);
    }
    @PostMapping("/{songId}/{playlistId}")
    public void createSongToPlaylist(@PathVariable Long songId, @PathVariable Long playlistId){
        service.createSongToPlaylist(songId,playlistId);
    }
    @PutMapping("/{id}")
    public void update(@RequestBody PlaylistRequest request, @PathVariable Long id){
        service.update(request,id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @DeleteMapping("/{songId}/{playlistId}")
    public void deleteSongToPlaylist(@PathVariable Long songId, @PathVariable Long playlistId) {
        service.deleteSongToPlaylist(songId,playlistId);
    }
    @GetMapping("/{id}")
    public PlaylistResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping()
    public List<PlaylistResponse> getAll() {
        return service.getAll();
    }
}
