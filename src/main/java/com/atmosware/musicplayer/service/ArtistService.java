package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;

import java.util.List;

public interface ArtistService {
    void create(ArtistRequest request);
    //void createDemandForApproval(ArtistRequest request);
    void update(ArtistRequest request, Long id);
    void delete(Long id);
    ArtistResponse getById(Long id);
    List<ArtistResponse> getAll();
    ArtistResponse getByName(String name);
}
