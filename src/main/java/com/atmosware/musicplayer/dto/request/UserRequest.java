package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String nationalIdentity;
    private LocalDateTime dateOfBirth;
    private Set<Long> roleIds;
}
