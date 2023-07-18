package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.CommentRequest;
import com.atmosware.musicplayer.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    void create(CommentRequest request);

    void update(CommentRequest request, Long id);

    void delete(Long id);

    CommentResponse getById(Long id);

    List<CommentResponse> getAll();

    CommentResponse findBySongId(Long id);

    CommentResponse findByUser_Id(Long id);
}
