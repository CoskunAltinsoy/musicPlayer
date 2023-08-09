package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.AlbumConverter;
import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Image;
import com.atmosware.musicplayer.repository.AlbumRepository;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.ImageService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository repository;
    private final ArtistService artistService;
    private final ImageService imageService;
    private final AlbumConverter converter;

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result create(AlbumRequest request) {
        repository.existsByNameAndArtist_Id(request.getName(), request.getArtistId())
                .orElseThrow(() -> new BusinessException(Message.Album.NOT_EXIST));
        Artist artist = artistService.findById(request.getArtistId());
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        Album album = converter.convertToEntity(request);
        album.setId(0L);
        album.setImage(image);
        album.setArtist(artist);
        album.setCreatedDate(LocalDateTime.now());
        repository.save(album);
        return new Result(Message.Album.SUCCESSFUL);
    }

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result update(AlbumRequest request, Long id) {
        checkIfAlbumExistsById(id);
        Artist artist = artistService.findById(request.getArtistId());
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        Album album = repository.findById(id).orElseThrow();
        album.setArtist(artist);
        album.setImage(image);
        album.setName(request.getName());
        album.setUpdatedDate(LocalDateTime.now());
        repository.save(album);
        return new Result(Message.Album.SUCCESSFUL);
    }

    @CacheEvict(value = "albums", allEntries = true)
    @Override
    public Result delete(Long id) {
        checkIfAlbumExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Album.SUCCESSFUL);
    }
    @Cacheable(value = "albums", key = "#id")
    @Override
    public DataResult<AlbumResponse> getById(Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        var artistResponse = artistService.getById(album.getArtist().getId());
        byte[] byteImage = imageService.downloadImageFromFileSystem(album.getImage().getFilePath());
        AlbumResponse albumResponse = converter.convertToResponse(album);
        albumResponse.setArtistResponse(artistResponse.getData());
        albumResponse.setImage(byteImage);
        return new DataResult<AlbumResponse>(Message.Album.SUCCESSFUL,albumResponse);
    }

    @Cacheable(value = "albums")
    @Override
    public DataResult<List<AlbumResponse>> getAll() {
        List<Album> albums = repository.findAll();
        var responses = albums
                .stream()
                .map(album -> {
                    byte[] byteImage = imageService.downloadImageFromFileSystem(album.getImage().getFilePath());
                    AlbumResponse albumResponse = converter.convertToResponse(album);
                    var artistResponse = artistService.getById(album.getArtist().getId());
                    albumResponse.setArtistResponse(artistResponse.getData());
                    albumResponse.setImage(byteImage);
                    return albumResponse;
                })
                .toList();
        return new DataResult<List<AlbumResponse>>(Message.Album.SUCCESSFUL,responses);
    }
    @Cacheable(value = "albums", key = "#name")
    @Override
    public DataResult<AlbumResponse> getByName(String name) {
        Album album = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BusinessException("There is no album by this name: " + name));
        var artistResponse = artistService.getById(album.getArtist().getId());
        byte[] byteImage = imageService.downloadImageFromFileSystem(album.getImage().getFilePath());
        AlbumResponse albumResponse = converter.convertToResponse(album);
        albumResponse.setArtistResponse(artistResponse.getData());
        albumResponse.setImage(byteImage);
        return new DataResult<AlbumResponse>(Message.Album.SUCCESSFUL,albumResponse);
    }
    @Cacheable(value = "albums")
    @Override
    public DataResult<List<AlbumResponse>> getByReleasedYearGreaterThan(LocalDateTime year) {
        List<Album> albums = repository.findByReleasedYearGreaterThan(year);
        var responses = albums
                .stream()
                .map(album -> {
                    byte[] byteImage = imageService.downloadImageFromFileSystem(album.getImage().getFilePath());
                    AlbumResponse albumResponse = converter.convertToResponse(album);
                    var artistResponse = artistService.getById(album.getArtist().getId());
                    albumResponse.setArtistResponse(artistResponse.getData());
                    albumResponse.setImage(byteImage);
                    return albumResponse;
                })
                .toList();
        return new DataResult<List<AlbumResponse>>(Message.Album.SUCCESSFUL,responses);
    }

    @Override
    public Album findById(Long id) {
        checkIfAlbumExistsById(id);
        return repository.findById(id).orElseThrow();
    }

    private void checkIfAlbumExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("This Album has not registered in the system");
        }
    }
}
