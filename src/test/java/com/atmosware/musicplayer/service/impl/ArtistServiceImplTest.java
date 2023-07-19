package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.ArtistConverter;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.service.ArtistService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArtistServiceImplTest {
    @Autowired
    private ArtistService service;
    @Autowired
    private ArtistConverter converter;

    @Test
    public void create() {
        Artist artist = new Artist();
        artist.setName("Erkin Koray");
        artist.setDescription("Erkin Koray");

        ArtistRequest request = converter.convertToRequest(artist);
        service.create(request);
    }
    @Test
    public void update() {
        Artist artist = new Artist();
        artist.setName("Erkin Koray");
        artist.setDescription("Erkin Koray");

        ArtistRequest request =converter.convertToRequest(artist);
        service.update(request, 2L);
    }
}