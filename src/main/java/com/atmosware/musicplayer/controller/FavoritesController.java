package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoritesController {
    private final FavoritesService service;

    @PostMapping("/{songId}/{favoriteId}")
    public void createSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        service.createSongToFavorite(songId,favoriteId);
    }
    @DeleteMapping("/{songId}/{favoriteId}")
    public void deleteSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        service.deleteSongToFavorite(songId,favoriteId);
    }
    @GetMapping("/{id}")
    public FavoriteResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping()
    public List<FavoriteResponse> getAll() {
        return service.getAll();
    }
}
