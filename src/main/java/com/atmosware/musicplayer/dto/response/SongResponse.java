package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongResponse {
    private Long id;
    private AlbumResponse album;
    private Set<GenreResponse> genres;
    private String name;
}
