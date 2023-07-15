package com.atmosware.musicplayer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @Min(0)
    private Long songId;
    @Min(0)
    private Long userId;
    @NotBlank
    @Length(min = 1, max = 200, message = "length must be between 2 and 200")
    private String content;

}
