package com.atmosware.musicplayer.dto.response;

import com.atmosware.musicplayer.model.entity.Song;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistResponse {
    private Long id;
    private Set<SongResponse> songs;
    private String name;
}
