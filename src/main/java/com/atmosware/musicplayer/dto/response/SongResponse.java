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

public class SongResponse {
    private Long albumId;
    private Long artistId;
    private Set<Long> genreIds;
    private String name;
}
