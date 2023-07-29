package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenresController {
    private final GenreService service;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody GenreRequest request){
        service.create(request);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody GenreRequest request, @PathVariable Long id){
        service.update(request,id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }
    @GetMapping("/{id}")
    public GenreResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<GenreResponse> getAll() {
        return service.getAll();
    }
}