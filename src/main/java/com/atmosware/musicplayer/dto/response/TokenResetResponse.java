package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResetResponse implements Serializable {
    private String urlWithToken;
}
