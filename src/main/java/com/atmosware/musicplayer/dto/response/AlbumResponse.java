package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumResponse implements Serializable{
    private Long id;
    private ArtistResponse artistResponse;
    private String name;
    private LocalDateTime releasedYear;
    private byte[] image;
}
