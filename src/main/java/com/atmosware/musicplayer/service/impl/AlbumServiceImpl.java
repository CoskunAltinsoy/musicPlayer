package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.repository.AlbumRepository;
import com.atmosware.musicplayer.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository repository;
    private final ModelMapper mapper;
    @Override
    public void create(AlbumRequest request) {
        checkIfAlbumExistsByNameAndArtistId(request.getName(), request.getArtistId());
        Album album = mapper.map(request, Album.class);
        album.setId(0L);
        album.setCreatedDate(LocalDateTime.now());
        repository.save(album);
    }

    @Override
    public void update(AlbumRequest request, Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        album.setUpdatedDate(LocalDateTime.now());
        Album updatedAlbum = mapper.map(request, Album.class);
        repository.save(updatedAlbum);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public AlbumResponse getById(Long id) {
        checkIfAlbumExistsById(id);
        Album album = repository.findById(id).orElseThrow();
        AlbumResponse response = mapper.map(album,AlbumResponse.class);
        return response;
    }

    @Override
    public List<AlbumResponse> getAll() {
        List<Album> albums = repository.findAll();
        List<AlbumResponse> responses = albums
                .stream()
                .map(album -> mapper.map(album,AlbumResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public AlbumResponse getByName(String name) {
        checkIfAlbumExistsById(name);
        Album album = repository.findByName(name);
        AlbumResponse response = mapper.map(album, AlbumResponse.class);
        return response;
    }

    @Override
    public List<AlbumResponse> findByReleasedYearGreaterThan(LocalDateTime year) {
        List<Album> albums = repository.findByReleasedYearGreaterThan(year);
        List<AlbumResponse> responses = albums
                .stream()
                .map(album -> mapper.map(album, AlbumResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    private void checkIfAlbumExistsByNameAndArtistId(String name, Long artistId) {
        if (!repository.existsByNameAndArtistId(name, artistId)) {
            throw new BusinessException("There is no album by this name and artist");
        }
    }

    private void checkIfAlbumExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("This Album has not registered in the system");
        }
    }

    private void checkIfAlbumExistsById(String name) {
        if (!repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("There is no album by this name: " + name);
        }
    }
}
