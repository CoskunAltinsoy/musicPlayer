package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistController {
    private final ArtistService service;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    public Result create(@RequestBody ArtistRequest request) {
        return service.create(request);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    public Result update(@RequestBody ArtistRequest request, @PathVariable Long id) {
         return service.update(request, id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ARTIST')")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping("/{id}")

    public DataResult<ArtistResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('ARTIST') or hasAuthority('USER')")
    public DataResult<List<ArtistResponse>> getAll() {
        return service.getAll();
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/follow-artist/{followerId}/{followedArtistId}")
    public Result followArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        return service.followArtist(followerId, followedArtistId);
    }
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/unfollow-artist/{followerId}/{followedArtistId}")
    public Result unfollowArtist(@PathVariable Long followerId, @PathVariable Long followedArtistId) {
        return service.unfollowArtist(followerId, followedArtistId);
    }
}

