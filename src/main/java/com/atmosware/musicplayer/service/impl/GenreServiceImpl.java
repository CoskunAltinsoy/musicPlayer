package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.GenreConverter;
import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.repository.GenreRepository;
import com.atmosware.musicplayer.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;
    private final GenreConverter converter;

    @Override
    public void create(GenreRequest request) {
        checkIfAlbumExistsByName(request.getName());
        Genre genre = converter.convertToEntity(request);
        genre.setId(0L);
        genre.setCreatedDate(LocalDateTime.now());
        repository.save(genre);
    }

    @Override
    public void update(GenreRequest request, Long id) {
        checkIfAlbumExistsByName(request.getName());
        Genre genre = repository.findById(id).orElseThrow();
        genre.setUpdatedDate(LocalDateTime.now());
        repository.save(genre);
    }

    @Override
    public void delete(Long id) {
        checkIfAlbumExistsById(id);
        repository.deleteById(id);
    }

    @Override
    public GenreResponse getById(Long id) {
        checkIfAlbumExistsById(id);
        Genre genre = repository.findById(id).orElseThrow();
        return converter.convertToResponse(genre);
    }

    @Override
    public List<GenreResponse> getAll() {
        List<Genre> genres = repository.findAll();
        return genres
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Genre findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    private void checkIfAlbumExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("There is no genre by this name: " + name);
        }
    }

    private void checkIfAlbumExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("There is no such genre");
        }
    }
}
