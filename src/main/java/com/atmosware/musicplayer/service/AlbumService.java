package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.AlbumRequest;
import com.atmosware.musicplayer.dto.response.AlbumResponse;
import com.atmosware.musicplayer.model.entity.Album;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import org.hibernate.boot.model.relational.Database;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface AlbumService {
    Result create(AlbumRequest request);

    Result update(AlbumRequest request, Long id);

    Result delete(Long id);

    DataResult<AlbumResponse> getById(Long id);

    DataResult<List<AlbumResponse>> getAll();

    DataResult<AlbumResponse> getByName(String name);

    Album findById(Long id);
}
