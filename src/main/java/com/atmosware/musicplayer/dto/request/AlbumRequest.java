package com.atmosware.musicplayer.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlbumRequest {
    @Min(0)
    private Long artistId;
    @NotBlank
    @Length(min = 1, message = "length must be greater than 1")
    private String name;
    private LocalDateTime releasedYear;
    private MultipartFile file;
}
