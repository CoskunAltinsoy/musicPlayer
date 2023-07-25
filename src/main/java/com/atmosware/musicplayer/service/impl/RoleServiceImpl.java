package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.RoleConverter;
import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.repository.RoleRepository;
import com.atmosware.musicplayer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final RoleConverter converter;
    @Override
    public void create(RoleRequest request) {
        Role role = converter.convertToEntity(request);
        role.setId(0L);
        repository.save(role);
    }
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    @Override
    public List<RoleResponse> getAll() {
        List<Role> roles = repository.findAll();
        return roles.stream()
                .map(role -> converter.convertToResponse(role))
                .toList();
    }
    @Override
    public Role findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
