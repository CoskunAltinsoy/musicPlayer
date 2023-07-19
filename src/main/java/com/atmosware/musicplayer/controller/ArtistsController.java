package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistsController {
    private final ArtistService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ArtistRequest request){
        service.create(request);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody ArtistRequest request, @PathVariable Long id){
        service.update(request,id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @GetMapping("/{id}")
    public ArtistResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<ArtistResponse> getAll() {
        return service.getAll();
    }
}

