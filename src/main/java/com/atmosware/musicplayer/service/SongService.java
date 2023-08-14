package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.SongRequest;
import com.atmosware.musicplayer.dto.response.SongResponse;
import com.atmosware.musicplayer.model.entity.Song;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SongService {
    Result create(SongRequest request);
    Result update(SongRequest request, Long id);
    Result delete(Long id);
    DataResult<SongResponse> getById(Long id);
    DataResult<List<SongResponse>> getAll();
    DataResult<List<SongResponse>> getAllByArtistName(String name);
    DataResult<List<SongResponse>>getAllByAlbumName(String name);
    DataResult<List<SongResponse>> getAllByGenreName(String name);
    DataResult<List<SongResponse>> getMostFavoriteSongs(int page, int size);
    Song findById(Long id);
}
