package com.atmosware.musicplayer.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistResponse {
    private Long id;
    private String name;
    private String description;
}
