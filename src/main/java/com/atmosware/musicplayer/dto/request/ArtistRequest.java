package com.atmosware.musicplayer.dto.request;

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
public class ArtistRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Length(min = 2, max = 100, message = "length must be between 2 and 100")
    private String description;
}
