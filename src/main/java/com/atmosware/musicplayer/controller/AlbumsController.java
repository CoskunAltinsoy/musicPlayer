package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumsController {
    private final AlbumService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AlbumRequest request){
        service.create(request);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody AlbumRequest request, @PathVariable Long id){
        service.update(request,id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @GetMapping("/{id}")
    public AlbumResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public List<AlbumResponse> getAll() {
        return service.getAll();
    }
}
