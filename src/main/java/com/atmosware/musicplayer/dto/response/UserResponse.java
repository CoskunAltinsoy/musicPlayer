package com.atmosware.musicplayer.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String nationalIdentity;
    private LocalDateTime dateOfBirth;
    private List<Long> commentIds;
    private List<Long> playlistIds;
    private Set<RoleResponse> roles;
}
