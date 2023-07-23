package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongConverter {
    private final GenreConverter genreConverter;
    public Song convertToEntity(SongRequest request) {
        Song song = new Song();
        song.setName(request.getName());
        return song;
    }

    public SongResponse convertToResponse(Song song) {
        return SongResponse.builder()
                .id(song.getId())
                .genres(genreConverter.convertToResponseList(song.getGenres()))
                .name(song.getName())
                .build();
    }

    public SongResponse convertToRequest(Song song) {
        return SongResponse.builder()
                .name(song.getName())
                .build();
    }
}
