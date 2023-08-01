package com.atmosware.musicplayer.dto.response;

import com.atmosware.musicplayer.model.entity.Song;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistResponse implements Serializable {
    private Long id;
    private Set<SongResponse> songs;
    private String name;
}
