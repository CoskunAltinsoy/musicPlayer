package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.service.GenreService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GenreServiceImplTest {
    @Autowired
    private GenreService genreService;
    @Autowired
    private ModelMapper mapper;

    @Test
    public void create(){
        Genre genre = new Genre();
        genre.setName("rock");

        GenreRequest request = mapper.map(genre, GenreRequest.class);
        genreService.create(request);
    }
}