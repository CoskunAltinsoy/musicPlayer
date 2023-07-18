package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongRequest {
    @Min(0)
    private Long albumId;
    @Min(0)
    private Long artistId;
    private Set<Long> genreIds;
    @NotBlank
    private String name;
}
