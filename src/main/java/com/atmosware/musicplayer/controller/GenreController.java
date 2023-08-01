package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService service;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Result create(@RequestBody GenreRequest request){
        return service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Result update(@RequestBody GenreRequest request, @PathVariable Long id){
        return service.update(request,id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping("/{id}")
    public DataResult<GenreResponse> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping()
    public DataResult<List<GenreResponse>> getAll() {
        return service.getAll();
    }
}