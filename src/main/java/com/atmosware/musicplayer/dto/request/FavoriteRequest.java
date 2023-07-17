package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest {
    private Set<Long> albumIds;
    private Set<Long> artistIds;
    private Set<Long> playlistIds;
    @Min(0)
    private Long userId;
}
