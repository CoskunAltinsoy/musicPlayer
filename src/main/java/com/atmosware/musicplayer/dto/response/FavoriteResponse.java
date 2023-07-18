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
    private Long id;
    private Set<Long> albumIds;
    private Set<Long> artistIds;
    private Set<Long> playlistIds;
    @Min(0)
    private Long userId;
}
