package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.GenreConverter;
import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.repository.GenreRepository;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
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
    public Result create(GenreRequest request) {
        checkIfGenreExistsByName(request.getName());
        Genre genre = converter.convertToEntity(request);
        genre.setId(0L);
        genre.setCreatedDate(LocalDateTime.now());
        repository.save(genre);
        return new Result(Message.Genre.successful);
    }

    @Override
    public Result update(GenreRequest request, Long id) {
        checkIfGenreExistsByName(request.getName());
        Genre genre = repository.findById(id).orElseThrow();
        genre.setUpdatedDate(LocalDateTime.now());
        repository.save(genre);
        return new Result(Message.Genre.successful);
    }

    @Override
    public Result delete(Long id) {
        checkIfGenreExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Genre.successful);
    }

    @Override
    public DataResult<GenreResponse> getById(Long id) {
        checkIfGenreExistsById(id);
        Genre genre = repository.findById(id).orElseThrow();
        var genreResponse = converter.convertToResponse(genre);
        return new DataResult<GenreResponse>(Message.Genre.successful,genreResponse);
    }

    @Override
    public DataResult<List<GenreResponse>> getAll() {
        List<Genre> genres = repository.findAll();
        var responses = genres
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
        return new DataResult<List<GenreResponse>>(Message.Genre.successful,responses);
    }

    @Override
    public Genre findById(Long id) {
        checkIfGenreExistsById(id);
        return repository.findById(id).orElseThrow();
    }

    private void checkIfGenreExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Message.Genre.notExist);
        }
    }

    private void checkIfGenreExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Genre.notExist);
        }
    }
}
