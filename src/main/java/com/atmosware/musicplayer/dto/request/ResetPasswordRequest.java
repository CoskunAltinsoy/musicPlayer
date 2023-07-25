package com.atmosware.musicplayer.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    String email;
}
