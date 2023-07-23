package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongsController {
    private final SongService service;

    @PostMapping
    public void create(@RequestBody SongRequest request){
        service.create(request);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody SongRequest request, @PathVariable Long id){
        service.update(request,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/id/{id}")
    public SongResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<SongResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/albumId/{albumId}")
    public List<SongResponse> getAllByAlbum(@PathVariable Long albumId) {
        return service.getAllByAlbum(albumId);
    }
}
