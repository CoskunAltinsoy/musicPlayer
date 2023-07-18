package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class AlbumServiceImplTest {
    @Autowired
    private AlbumService service;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private ModelMapper mapper;

    @Test
    public void create() {
        ArtistResponse response = artistService.getById(2L);
        Artist artist = mapper.map(response, Artist.class);

        Album album = new Album();
        album.setName("Ele Güne Karşı Yapayalnız.");
        album.setArtist(artist);
        album.setReleasedYear(LocalDateTime.of(1990, Month.APRIL, 15, 00, 00));

        AlbumRequest request = mapper.map(album, AlbumRequest.class);
        service.create(request);
    }
}