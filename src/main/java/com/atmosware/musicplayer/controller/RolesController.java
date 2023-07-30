package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RolesController {
    private final RoleService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Result create(@RequestBody RoleRequest request) {
        return service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public DataResult<List<RoleResponse>> getall() {
        return service.getAll();
    }
}
