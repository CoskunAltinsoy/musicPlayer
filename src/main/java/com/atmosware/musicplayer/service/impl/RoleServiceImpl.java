package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.RoleConverter;
import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.enums.RoleType;
import com.atmosware.musicplayer.repository.RoleRepository;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final RoleConverter converter;
    @Override
    public Result create(RoleRequest request) {
        Role role = converter.convertToEntity(request);
        role.setCreatedDate(LocalDateTime.now());
        repository.save(role);
        return new Result(Message.Role.SUCCESSFUL);
    }
    @Override
    public Result delete(Long id) {
        checkIfRoleExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Role.SUCCESSFUL);
    }
    @Override
    public DataResult<List<RoleResponse>> getAll() {
        List<Role> roles = repository.findAll();
        var responses = roles.stream()
                .map(role -> converter.convertToResponse(role))
                .toList();
        return new DataResult<>(Message.Role.SUCCESSFUL,responses);
    }
    @Override
    public Role findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Message.Role.NOT_EXIST));
    }

    @Override
    public Role findByName(RoleType name) {
        return repository.findByName(name)
                .orElseThrow(() -> new BusinessException(Message.Role.NOT_EXIST));
    }

    private void checkIfRoleExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Role.NOT_EXIST);
        }
    }
}
