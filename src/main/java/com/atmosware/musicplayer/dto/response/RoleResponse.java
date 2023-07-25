package com.atmosware.musicplayer.dto.response;

import com.atmosware.musicplayer.model.enums.RoleType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private RoleType name;
}