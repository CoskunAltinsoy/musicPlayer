package com.atmosware.musicplayer.dto.request;

import jakarta.mail.Multipart;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongRequest {
    @Min(0)
    private Long albumId;
    private Set<Long> genreIds;
    @NotBlank
    private String name;
}
