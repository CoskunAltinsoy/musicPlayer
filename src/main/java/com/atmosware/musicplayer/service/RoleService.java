package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface RoleService {
    Result create(RoleRequest request);

    Result delete(Long id);
    DataResult<List<RoleResponse>> getAll();
    Role findById(Long id);
}
