package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.ArtistConverter;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository repository;
    private final ArtistConverter converter;

    @Override
    public void create(ArtistRequest request) {
        Artist artist = converter.convertToEntity(request);
        artist.setId(0L);
        artist.setVerified(true);
        artist.setCreatedDate(LocalDateTime.now());
        repository.save(artist);
    }

    @Override
    public void update(ArtistRequest request, Long id) {
        Artist artist = repository.findById(id).orElseThrow();
        artist.setName(request.getName());
        artist.setDescription(request.getDescription());
        artist.setUpdatedDate(LocalDateTime.now());
        repository.save(artist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ArtistResponse getById(Long id) {
        Artist artist = repository.findById(id).orElseThrow();
        return converter.convertToResponse(artist);
    }

    @Override
    public List<ArtistResponse> getAll() {
        List<Artist> artists = repository.findAll();
        return artists
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse getByName(String name) {
        Artist artist = repository.findByNameIgnoreCase(name);
        return converter.convertToResponse(artist);
    }

    @Override
    public Artist findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
