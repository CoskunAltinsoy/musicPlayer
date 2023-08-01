package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.AlbumConverter;
import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.repository.AlbumRepository;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository repository;
    private final ArtistService artistService;
    private final AlbumConverter converter;

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result create(AlbumRequest request) {
        checkIfAlbumExistsByNameAndArtistId(request.getName(), request.getArtistId());
        Artist artist = artistService.findById(request.getArtistId());
        Album album = converter.convertToEntity(request);
        album.setId(0L);
        album.setArtist(artist);
        album.setCreatedDate(LocalDateTime.now());
        repository.save(album);
        return new Result(Message.Album.successful);
    }

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result update(AlbumRequest request, Long id) {
        checkIfAlbumExistsById(id);
        Artist artist = artistService.findById(request.getArtistId());
        Album album = repository.findById(id).orElseThrow();
        album.setArtist(artist);
        album.setName(request.getName());
        album.setUpdatedDate(LocalDateTime.now());
        repository.save(album);
        return new Result(Message.Album.successful);
    }

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result delete(Long id) {
        checkIfAlbumExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Album.successful);
    }
    @Cacheable(value = "albums", key = "#id")
    @Override
    public DataResult<AlbumResponse> getById(Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        var artistResponse = artistService.getById(album.getArtist().getId());
        AlbumResponse albumResponse = converter.convertToResponse(album);
        albumResponse.setArtistResponse(artistResponse.getData());
        return new DataResult<AlbumResponse>(Message.Album.successful,albumResponse);
    }

    @Cacheable(value = "albums")
    @Override
    public DataResult<List<AlbumResponse>> getAll() {
        List<Album> albums = repository.findAll();
        var responses = albums
                .stream()
                .map(album -> {
                    AlbumResponse albumResponse = converter.convertToResponse(album);
                    var artistResponse = artistService.getById(album.getArtist().getId());
                    albumResponse.setArtistResponse(artistResponse.getData());
                    return albumResponse;
                })
                .toList();
        return new DataResult<List<AlbumResponse>>(Message.Album.successful,responses);
    }
    @Cacheable(value = "albums", key = "#name")
    @Override
    public DataResult<AlbumResponse> getByName(String name) {
        checkIfAlbumExistsByName(name);
        Album album = repository.findByNameIgnoreCase(name);
        var artistResponse = artistService.getByName(album.getArtist().getName());
        AlbumResponse albumResponse = converter.convertToResponse(album);
        albumResponse.setArtistResponse(artistResponse.getData());
        return new DataResult<AlbumResponse>(Message.Album.successful,albumResponse);
    }
    @Cacheable(value = "albums")
    @Override
    public DataResult<List<AlbumResponse>> getByReleasedYearGreaterThan(LocalDateTime year) {
        List<Album> albums = repository.findByReleasedYearGreaterThan(year);
        var responses = albums
                .stream()
                .map(album -> {
                    AlbumResponse albumResponse = converter.convertToResponse(album);
                    var artistResponse = artistService.getById(album.getArtist().getId());
                    albumResponse.setArtistResponse(artistResponse.getData());
                    return albumResponse;
                })
                .toList();
        return new DataResult<List<AlbumResponse>>(Message.Album.successful,responses);
    }

    @Override
    public Album findById(Long id) {
        checkIfAlbumExistsById(id);
        return repository.findById(id).orElseThrow();
    }

    private void checkIfAlbumExistsByNameAndArtistId(String name, Long artistId) {
        if (repository.existsByNameAndArtist_Id(name, artistId)) {
            throw new BusinessException("There is already an album with that name");
        }
    }

    private void checkIfAlbumExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("This Album has not registered in the system");
        }
    }

    private void checkIfAlbumExistsByName(String name) {
        if (!repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("There is no album by this name: " + name);
        }
    }
}
