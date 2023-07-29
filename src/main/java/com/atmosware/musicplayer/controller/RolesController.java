package com.atmosware.musicplayer.controller;

import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.service.RoleService;
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
    public void create(@RequestBody RoleRequest request) {
        service.create(request);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RoleResponse> getall() {
        return service.getAll();
    }
}
