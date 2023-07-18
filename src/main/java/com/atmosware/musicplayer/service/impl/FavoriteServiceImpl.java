package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.dto.request.FavoriteRequest;
import com.atmosware.musicplayer.dto.response.FavoriteResponse;
import com.atmosware.musicplayer.repository.FavoriteRepository;
import com.atmosware.musicplayer.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository repository;
    private final ModelMapper mapper;

    @Override
    public void create(FavoriteRequest request) {

    }

    @Override
    public void update(FavoriteRequest request, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public FavoriteResponse getById(Long id) {
        return null;
    }

    @Override
    public List<FavoriteResponse> getAll() {
        return null;
    }
}
