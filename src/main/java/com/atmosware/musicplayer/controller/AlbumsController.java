package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumsController {
    private final AlbumService service;
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PostMapping
    public void create(@RequestBody AlbumRequest request){
        service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PutMapping("/{id}")
    public void update(@RequestBody AlbumRequest request, @PathVariable Long id){
        service.update(request,id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @DeleteMapping("/{id}")
    @ResponseStatus
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/id/{id}")
    public AlbumResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<AlbumResponse> getAll() {
        return service.getAll();
    }
    @GetMapping("/name/{name}")
    public AlbumResponse getByName(@PathVariable String name) {
        return service.getByName(name);
    }
}
