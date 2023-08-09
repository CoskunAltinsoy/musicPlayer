package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.service.SongService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/id/{id}")
    public DataResult<SongResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public DataResult<List<SongResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/albumId/{albumId}")
    public DataResult<List<SongResponse>> getAllByAlbum(@PathVariable Long albumId) {
        return service.getAllByAlbum(albumId);
    }
}
