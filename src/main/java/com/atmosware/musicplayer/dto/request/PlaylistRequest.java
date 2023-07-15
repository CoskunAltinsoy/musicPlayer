package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistRequest {
    @Min(0)
    private Long userId;
    @Min(0)
    private Set<Long> songIds;
    @NotBlank
    private String name;

}
