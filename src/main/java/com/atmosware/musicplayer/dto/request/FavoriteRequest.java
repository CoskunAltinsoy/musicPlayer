package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequest {
    @Min(0)
    private Long albumId;
    @Min(0)
    private Long artistId;
    @Min(0)
    private Long playlistId;
    @Min(0)
    private Long userId;
}
