package com.atmosware.musicplayer.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistResponse {
    private Long userId;
    private Set<Long> songIds;
    private String name;
}
