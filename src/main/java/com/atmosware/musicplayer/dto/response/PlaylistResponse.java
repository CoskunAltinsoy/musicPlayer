package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistResponse {
    private Long id;
    private Long userId;
    private Set<Long> songIds;
    private String name;
}
