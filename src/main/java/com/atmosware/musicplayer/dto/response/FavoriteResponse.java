package com.atmosware.musicplayer.dto.response;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponse implements Serializable {
    private Set<SongResponse> songs;
}
