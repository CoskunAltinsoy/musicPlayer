package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
public class AlbumController {
    private final AlbumService service;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @PostMapping
    public Result create(@RequestBody AlbumRequest request){
         return service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @PutMapping("/{id}")
    public Result update(@RequestBody AlbumRequest request, @PathVariable Long id){
        return service.update(request,id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST')")
    @DeleteMapping("/{id}")
    @ResponseStatus
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/id/{id}")
    public DataResult<AlbumResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public DataResult<List<AlbumResponse>> getAll() {
        return service.getAll();
    }
    @GetMapping("/name/{name}")
    public DataResult<AlbumResponse> getByName(@PathVariable String name) {
        return service.getByName(name);
    }
}
