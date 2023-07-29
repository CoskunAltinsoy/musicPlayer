package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRequest {
    @Min(0)
    private Long userId;
}
