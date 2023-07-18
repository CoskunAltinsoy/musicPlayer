package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.AlbumConverter;
import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.repository.AlbumRepository;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
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

    @Override
    public void create(AlbumRequest request) {
        checkIfAlbumExistsByNameAndArtistId(request.getName(), request.getArtistId());
        Album album = converter.convertToEntity(request);
        album.setId(0L);
        album.setCreatedDate(LocalDateTime.now());
        repository.save(album);
    }

    @Override
    public void update(AlbumRequest request, Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        album.setUpdatedDate(LocalDateTime.now());
        Album updatedAlbum = converter.convertToEntity(album);
        repository.save(updatedAlbum);
    }

    @Override
    public void delete(Long id) {
        checkIfAlbumExistsById(id);
        repository.deleteById(id);
    }

    @Override
    public AlbumResponse getById(Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        return converter.convertToResponse(album);
    }

    @Override
    public List<AlbumResponse> getAll() {
        List<Album> albums = repository.findAll();
        return albums
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumResponse getByName(String name) {
        checkIfAlbumExistsByName(name);
        Album album = repository.findByName(name);
        return converter.convertToResponse(album);
    }

    @Override
    public List<AlbumResponse> findByReleasedYearGreaterThan(LocalDateTime year) {
        List<Album> albums = repository.findByReleasedYearGreaterThan(year);
        return albums
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
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
