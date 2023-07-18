package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    @Min(0)
    private Long songId;
    @Min(0)
    private Long userId;
    @NotBlank
    @Length(min = 1, max = 200, message = "length must be between 2 and 200")
    private String content;

}
