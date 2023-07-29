package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistsController {
    private final ArtistService service;

    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PostMapping
    public void create(@RequestBody ArtistRequest request){
        service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PutMapping("/{id}")
    public void update(@RequestBody ArtistRequest request, @PathVariable Long id){
        service.update(request,id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
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

