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
    private Long albumId;
    private Long artistId;
    private Set<Long> genreIds;
    private String name;
}
