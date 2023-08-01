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
import io.netty.handler.codec.MessageAggregationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;
    private final RoleConverter converter;
    @Override
    public Result create(RoleRequest request) {
        Role role = converter.convertToEntity(request);
        role.setId(0L);
        role.setCreatedDate(LocalDateTime.now());
        repository.save(role);
        return new Result(Message.Role.successful);
    }
    @Override
    public Result delete(Long id) {
        checkIfRoleExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Role.successful);
    }
    @Override
    public DataResult<List<RoleResponse>> getAll() {
        List<Role> roles = repository.findAll();
        var responses = roles.stream()
                .map(role -> converter.convertToResponse(role))
                .toList();
        return new DataResult<List<RoleResponse>>(Message.Role.successful,responses);
    }
    @Override
    public Role findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public Role findByName(RoleType name) {
        checkIfRoleExistsByName(name);
        return repository.findByName(name);
    }

    private void checkIfRoleExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Role.notExist);
        }
    }
    private void checkIfRoleExistsByName(RoleType name) {
        if (!repository.existsByName(name)) {
            throw new BusinessException(Message.Role.notExist);
        }
    }
}
