package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.PlaylistRequest;
import com.atmosware.musicplayer.dto.response.PlaylistResponse;
import com.atmosware.musicplayer.service.PlaylistService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlist")
public class PlaylistController {
    private final PlaylistService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result create(@RequestBody PlaylistRequest request){
        return service.create(request);
    }

    @PostMapping("/{songId}/{playlistId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result createSongToPlaylist(@PathVariable Long songId, @PathVariable Long playlistId){
        return service.createSongToPlaylist(songId,playlistId);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result update(@RequestBody PlaylistRequest request, @PathVariable Long id){
        return service.update(request,id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @DeleteMapping("/{songId}/{playlistId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result deleteSongToPlaylist(@PathVariable Long songId, @PathVariable Long playlistId) {
        return service.deleteSongToPlaylist(songId,playlistId);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public DataResult<PlaylistResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public DataResult<List<PlaylistResponse>> getAll() {
        return service.getAll();
    }
}
