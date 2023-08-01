package com.atmosware.musicplayer.dto.response;

import com.atmosware.musicplayer.model.enums.RoleType;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse implements Serializable {
    private RoleType name;
}