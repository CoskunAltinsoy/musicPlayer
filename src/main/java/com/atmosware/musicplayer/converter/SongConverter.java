package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<SongResponse> convertToResponseList(Set<Song> songs) {
        return songs.stream()
                .map(song -> convertToResponse(song))
                .collect(Collectors.toSet());
    }
    public Song convertToEntity(SongResponse response) {
        return Song.builder()
                .id(response.getId())
                .genres(genreConverter.convertToEntityList(response.getGenres()))
                .name(response.getName())
                .build();
    }
}
