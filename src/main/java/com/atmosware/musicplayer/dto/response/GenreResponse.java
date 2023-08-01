package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreResponse implements Serializable {
    private Long id;
    private String name;
}
