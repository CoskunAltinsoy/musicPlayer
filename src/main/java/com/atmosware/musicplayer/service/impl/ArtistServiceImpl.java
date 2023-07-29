package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.ArtistConverter;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository repository;
    @Lazy
    private final UserService userService;
    private final ArtistConverter converter;
    private final AuthenticationFacade authenticationFacade;


    @Override
    public void create(ArtistRequest request) {
        User user = userService.findByEmail(authenticationFacade.getUsername());
        checkIfApprovalArtistTrue(user);
        Artist artist = converter.convertToEntity(request);
        artist.setId(0L);
        artist.setVerified(true);
        artist.setCreatedDate(LocalDateTime.now());
        repository.save(artist);
    }

    @Override
    public void update(ArtistRequest request, Long id) {
        Artist artist = repository.findById(id).orElseThrow();
        artist.setName(request.getName());
        artist.setDescription(request.getDescription());
        artist.setUpdatedDate(LocalDateTime.now());
        repository.save(artist);
    }

    @Override
    public void delete(Long id) {
        checkIfArtistExistsById(id);
        repository.deleteById(id);
    }

    @Override
    public ArtistResponse getById(Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        return converter.convertToResponse(artist);
    }

    @Override
    public List<ArtistResponse> getAll() {
        List<Artist> artists = repository.findAll();
        return artists
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse getByName(String name) {
        checkIfArtistExistsByName(name);
        Artist artist = repository.findByNameIgnoreCase(name);
        return converter.convertToResponse(artist);
    }

    @Override
    public Artist findById(Long id) {
        checkIfArtistExistsById(id);
        return repository.findById(id).orElseThrow();
    }
    private void checkIfApprovalArtistTrue(User user){
        if (user.isApproveArtist() != true){
            throw new BusinessException("This user have not approved yet.");
        }
    }
    private void checkIfArtistExistsById(Long id){
        if (!repository.existsById(id)){
            throw new BusinessException("There is no such an artist.");
        }
    }
    private void checkIfArtistExistsByName(String name){
        if (!repository.existsByName(name)){
            throw new BusinessException("There is no such an artist.");
        }
    }
}
