package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse implements Serializable {
    private String token;
    private String refreshToken;
    private String email;
    private Set<String> roles;
}