package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponse {
    private Long id;
    private Long artistId;
    private String name;
    private LocalDateTime releasedYear;
}
