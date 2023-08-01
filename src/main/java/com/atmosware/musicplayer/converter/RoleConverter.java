package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.RoleRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.enums.RoleType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleConverter {
    public Role convertToEntity(RoleRequest request){
        return Role.builder()
                .name(request.getName())
                .build();
    }

    public RoleResponse convertToResponse(Role role){
        return RoleResponse.builder()
                .name(role.getName())
                .build();
    }
    public Set<RoleResponse> convertToResponseList(Set<Role> roles){
        return roles.stream()
                .map(role -> convertToResponse(role))
                .collect(Collectors.toSet());
    }
}
