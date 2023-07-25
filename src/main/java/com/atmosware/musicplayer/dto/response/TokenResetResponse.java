package com.atmosware.musicplayer.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResetResponse {
    private String urlWithToken;
}
