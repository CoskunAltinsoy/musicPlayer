package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/song")
public class SongController {
    private final SongService service;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @PostMapping
    public Result create(@RequestBody SongRequest request){
        return service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SongRequest request, @PathVariable Long id){
        return service.update(request,id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping("/id/{id}")
    public DataResult<SongResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping()
    public DataResult<List<SongResponse>> getAll() {
        return service.getAll();
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping("/artistName/{name}")
    public DataResult<List<SongResponse>>  getAllByArtistName(@RequestParam String name) {
        return service.getAllByArtistName(name);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping("/albumName/{name}")
    public DataResult<List<SongResponse>> getAllByAlbumName(@RequestParam String name) {
        return service.getAllByAlbumName(name);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping("/genreName/{name}")
    public DataResult<List<SongResponse>> getAllByGenreName(@RequestParam String name) {
        return service.getAllByGenreName(name);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    @GetMapping("/most-popular/{}")
    public DataResult<List<SongResponse>> getMostFavoriteSongs(@RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return service.getMostFavoriteSongs(page, size);
    }
}
