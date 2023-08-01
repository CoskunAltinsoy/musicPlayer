package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.GenreRequest;
import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.dto.response.GenreResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Genre;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.service.AlbumService;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.GenreService;
import com.atmosware.musicplayer.service.SongService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SongServiceImplTest {

}