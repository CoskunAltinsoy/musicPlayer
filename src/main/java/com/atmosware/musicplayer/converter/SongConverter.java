package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SongConverter {
    private final GenreConverter genreConverter;

    public Song convertToEntity(SongRequest request) {
        return Song.builder()
                .name(request.getName())
                .build();
    }

    public SongResponse convertToResponse(Song song) {
        return SongResponse.builder()
                .id(song.getId())
                .genres(genreConverter.convertToResponseList(song.getGenres()))
                .name(song.getName())
                .build();
    }
}
