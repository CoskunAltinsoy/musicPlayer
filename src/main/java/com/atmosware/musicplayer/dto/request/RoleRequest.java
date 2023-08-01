package com.atmosware.musicplayer.dto.request;

import com.atmosware.musicplayer.model.enums.RoleType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequest {
    private RoleType name;
}
