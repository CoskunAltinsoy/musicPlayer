package com.atmosware.musicplayer.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
