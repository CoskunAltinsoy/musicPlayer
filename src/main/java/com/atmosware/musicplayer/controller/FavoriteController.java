package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.service.FavoriteService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {
    private final FavoriteService service;

    @PostMapping("/{songId}")
    public Result createSongToFavorite(@PathVariable Long songId){
        return service.createSongToFavorite(songId);
    }
    @DeleteMapping("/{songId}/{favoriteId}")
    public Result deleteSongToFavorite(@PathVariable Long songId, @PathVariable Long favoriteId){
        return service.deleteSongToFavorite(songId,favoriteId);
    }
    @DeleteMapping("/{favoriteId}")
    public Result delete(@PathVariable Long favoriteId){
        return service.delete(favoriteId);
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
