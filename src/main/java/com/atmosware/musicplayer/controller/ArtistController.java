package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistController {
    private final ArtistService service;

    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PostMapping
    public Result create(@RequestBody ArtistRequest request) {
        return service.create(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @PutMapping("/{id}")
    public Result update(@RequestBody ArtistRequest request, @PathVariable Long id) {
         return service.update(request, id);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")
    public DataResult<ArtistResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public DataResult<List<ArtistResponse>> getAll() {
        return service.getAll();
    }
}

