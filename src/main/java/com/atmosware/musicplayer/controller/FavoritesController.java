package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.service.FavoritesService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesController {
    private final FavoritesService service;

    @PostMapping("/{songId}/{favoriteId}")
    public Result createSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        return service.createSongToFavorite(songId,favoriteId);
    }
    @DeleteMapping("/{songId}/{favoriteId}")
    public Result deleteSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        return service.deleteSongToFavorite(songId,favoriteId);
    }
    @GetMapping("/{id}")
    public DataResult<FavoriteResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping()
    public DataResult<List<FavoriteResponse>> getAll() {
        return service.getAll();
    }
}
