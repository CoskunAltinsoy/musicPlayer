package com.atmosware.musicplayer.converter;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Genre;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreConverter {
    public Genre convertToEntity(GenreRequest request) {
        return Genre.builder()
                .name(request.getName())
                .build();
    }
    public Genre convertToEntity(GenreResponse response) {
        return Genre.builder()
                .name(response.getName())
                .build();
    }

    public GenreResponse convertToResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
    public Set<GenreResponse> convertToResponseList(Set<Genre> genres) {
        return genres.stream()
                .map(genre -> convertToResponse(genre))
                .collect(Collectors.toSet());
    }
    public Set<Genre> convertToEntityList(Set<GenreResponse> responses) {
        return responses.stream()
                .map(response -> convertToEntity(response))
                .collect(Collectors.toSet());
    }
}
