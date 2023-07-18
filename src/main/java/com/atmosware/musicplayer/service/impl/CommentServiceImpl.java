package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.CommentRequest;
import com.atmosware.musicplayer.dto.response.CommentResponse;
import com.atmosware.musicplayer.model.entity.Comment;
import com.atmosware.musicplayer.repository.CommentRepository;
import com.atmosware.musicplayer.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;
    private final ModelMapper mapper;

    @Override
    public void create(CommentRequest request) {
        Comment comment = mapper.map(request, Comment.class);
        comment.setId(0L);
        comment.setCreatedDate(LocalDateTime.now());
        repository.save(comment);
    }

    @Override
    public void update(CommentRequest request, Long id) {
        Comment comment = repository.findById(id).orElseThrow();
        comment.setUpdatedDate(LocalDateTime.now());
        Comment updatedComment = mapper.map(request, Comment.class);
        repository.save(updatedComment);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public CommentResponse getById(Long id) {
        Comment comment = repository.findById(id).orElseThrow();
        CommentResponse response = mapper.map(comment, CommentResponse.class);
        return response;
    }

    @Override
    public List<CommentResponse> getAll() {
        List<Comment> comments = repository.findAll();
        List<CommentResponse> responses = comments
                .stream()
                .map(comment -> mapper.map(comment, CommentResponse.class))
                .collect(Collectors.toList());
        return responses;
    }

    @Override
    public CommentResponse findBySongId(Long id) {
        Comment comment = repository.findBySong_Id(id);
        CommentResponse response = mapper.map(comment, CommentResponse.class);
        return response;
    }

    @Override
    public CommentResponse findByUser_Id(Long id) {
        Comment comment = repository.findByUser_Id(id);
        CommentResponse response = mapper.map(comment, CommentResponse.class);
        return response;
    }
}
