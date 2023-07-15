package com.atmosware.musicplayer.dto.request;

import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequest {
    @NotBlank
    private String name;
}
