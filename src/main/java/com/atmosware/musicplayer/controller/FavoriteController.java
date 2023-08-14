package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.service.FavoriteService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {
    private final FavoriteService service;

    @PostMapping("/{songId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result createSongToFavorite(@PathVariable Long songId){
        return service.createSongToFavorite(songId);
    }
    @DeleteMapping("/{songId}/{favoriteId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result deleteSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        return service.deleteSongToFavorite(songId,favoriteId);
    }
    @DeleteMapping("/{favoriteId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public Result delete(@PathVariable Long favoriteId){
        return service.delete(favoriteId);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public DataResult<FavoriteResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public DataResult<List<FavoriteResponse>> getAll() {
        return service.getAll();
    }
}
