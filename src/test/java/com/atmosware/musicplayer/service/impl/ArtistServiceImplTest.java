package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArtistServiceImplTest {
    @Autowired
    private ArtistService service;
    @Autowired
    private ModelMapper mapper;

    @Test
    public void create(){
        Artist artist = new Artist();
        artist.setName("MFÖ");
        artist.setDescription("Mazhar, Fuat, Ozkan");

        ArtistRequest request = mapper.map(artist,ArtistRequest.class);
        service.create(request);
    }
}