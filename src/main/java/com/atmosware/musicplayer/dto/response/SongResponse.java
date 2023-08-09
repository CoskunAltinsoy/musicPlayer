package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongResponse implements Serializable {
    private Long id;
    private AlbumResponse album;
    private Set<GenreResponse> genres;
    private String name;
    private byte[] image;
}
