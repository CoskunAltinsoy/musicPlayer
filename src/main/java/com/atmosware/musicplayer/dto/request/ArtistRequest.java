package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArtistRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Length(min = 2, max = 100, message = "length must be between 2 and 100")
    private String description;
    private MultipartFile file;
}
