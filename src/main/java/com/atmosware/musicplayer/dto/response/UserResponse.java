package com.atmosware.musicplayer.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String nationalIdentity;
    private LocalDateTime dateOfBirth;
    private List<Long> commentIds;
    private List<Long> playlistIds;
    private Set<Long> roleIds;
}
