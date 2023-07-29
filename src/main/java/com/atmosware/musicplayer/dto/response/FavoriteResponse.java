package com.atmosware.musicplayer.dto.response;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponse {
    private Set<SongResponse> songs;
}
