package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.GenreConverter;
import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.repository.GenreRepository;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.util.TimeUtil;
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
    private final TimeUtil timeUtil;
    @Override
    public Result create(GenreRequest request) {
        checkIfGenreExistsByName(request.getName());
        Genre genre = converter.convertToEntity(request);
        genre.setCreatedDate(timeUtil.getLocalDateTimeNow());
        repository.save(genre);
        return new Result(Message.Genre.SUCCESSFUL);
    }

    @Override
    public Result update(GenreRequest request, Long id) {
        checkIfGenreExistsByName(request.getName());
        Genre genre = repository.findById(id).orElseThrow();
        genre.setUpdatedDate(timeUtil.getLocalDateTimeNow());
        repository.save(genre);
        return new Result(Message.Genre.SUCCESSFUL);
    }

    @Override
    public Result delete(Long id) {
        checkIfGenreExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Genre.SUCCESSFUL);
    }

    @Override
    public DataResult<GenreResponse> getById(Long id) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new BusinessException(Message.Genre.NOT_EXIST));
        var genreResponse = converter.convertToResponse(genre);
        return new DataResult<>(Message.Genre.SUCCESSFUL,genreResponse);
    }

    @Override
    public DataResult<List<GenreResponse>> getAll() {
        List<Genre> genres = repository.findAll();
        var responses = genres
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
        return new DataResult<>(Message.Genre.SUCCESSFUL,responses);
    }

    @Override
    public Genre findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(Message.Genre.NOT_EXIST));
    }

    private void checkIfGenreExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Genre.NOT_EXIST);
        }
    }
    private void checkIfGenreExistsByName(String name) {
        if (!repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Message.Genre.NOT_EXIST);
        }
    }
}
