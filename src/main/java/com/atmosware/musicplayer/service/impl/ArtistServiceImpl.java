package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository repository;
    private final ModelMapper mapper;
    @Override
    public void create(ArtistRequest request) {
        Artist artist = mapper.map(request, Artist.class);
        int numberOfSongs = artist.getSongs().size();
        int numberOfAlbums = artist.getAlbums().size();
        artist.setId(0L);
        artist.setVerified(true);
        artist.setNumberOfSongs(numberOfSongs);
        artist.setNumberOfAlbum(numberOfAlbums);
        artist.setCreatedDate(LocalDateTime.now());
        repository.save(artist);
    }

    @Override
    public void update(ArtistRequest request, Long id) {
        Artist artist = repository.findById(id).orElseThrow();
        artist.setUpdatedDate(LocalDateTime.now());
        Artist updatedArtist = mapper.map(request, Artist.class);
        repository.save(updatedArtist);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ArtistResponse getById(Long id) {
        Artist artist = repository.findById(id).orElseThrow();
        ArtistResponse response = mapper.map(artist, ArtistResponse.class);
        return response;
    }

    @Override
    public List<ArtistResponse> getAll() {
        List<Artist> artists = repository.findAll();
        List<ArtistResponse> responses = artists
                .stream()
                .map(artist -> mapper.map(artist,ArtistResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public ArtistResponse getByName(String name) {
        Artist artist = repository.findByNameIgnoreCase(name);
        ArtistResponse response = mapper.map(artist, ArtistResponse.class);
        return response;
    }
}
