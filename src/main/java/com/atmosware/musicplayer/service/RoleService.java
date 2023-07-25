package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.model.entity.Role;

import java.util.List;

public interface RoleService {
    void create(RoleRequest request);

    void delete(Long id);

    List<RoleResponse> getAll();
    Role findById(Long id);
}
