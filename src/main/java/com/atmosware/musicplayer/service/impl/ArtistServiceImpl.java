package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.ArtistConverter;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.model.enums.RoleType;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import io.netty.handler.codec.MessageAggregationException;
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
    private final RoleService roleService;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Result create(ArtistRequest request) {
        User user = userService.findByEmail(authenticationFacade.getUsername());
        checkIfApprovalArtistTrue(user);
        Role role = roleService.findByName(RoleType.ARTIST);
        user.getRoles().add(role);
        Artist artist = converter.convertToEntity(request);
        artist.setId(0L);
        artist.setVerified(true);
        artist.setCreatedDate(LocalDateTime.now());
        repository.save(artist);
        return new Result(Message.Artist.successful);
    }

    @Override
    public Result update(ArtistRequest request, Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        artist.setName(request.getName());
        artist.setDescription(request.getDescription());
        artist.setUpdatedDate(LocalDateTime.now());
        repository.save(artist);
        return new Result(Message.Artist.successful);
    }

    @Override
    public Result delete(Long id) {
        checkIfArtistExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Artist.successful);
    }

    @Override
    public DataResult<ArtistResponse> getById(Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        var artistResponse = converter.convertToResponse(artist);
        return new DataResult<ArtistResponse>(Message.Artist.successful,artistResponse);
    }

    @Override
    public DataResult<List<ArtistResponse>> getAll() {
        List<Artist> artists = repository.findAll();
        var responses = artists
                .stream()
                .map(converter::convertToResponse)
                .collect(Collectors.toList());
        return new DataResult<List<ArtistResponse>>(Message.Artist.successful,responses);
    }

    @Override
    public DataResult<ArtistResponse> getByName(String name) {
        checkIfArtistExistsByName(name);
        Artist artist = repository.findByNameIgnoreCase(name);
        var artistResponse = converter.convertToResponse(artist);
        return new DataResult<ArtistResponse>(Message.Artist.successful,artistResponse);
    }

    @Override
    public Artist findById(Long id) {
        checkIfArtistExistsById(id);
        return repository.findById(id).orElseThrow();
    }
    private void checkIfApprovalArtistTrue(User user){
        if (user.isApproveArtist() != true){
            throw new BusinessException(Message.Artist.notApprovedYet);
        }
    }
    private void checkIfArtistExistsById(Long id){
        if (!repository.existsById(id)){
            throw new BusinessException(Message.Artist.notExist);
        }
    }
    private void checkIfArtistExistsByName(String name){
        if (!repository.existsByName(name)){
            throw new BusinessException(Message.Artist.notExist);
        }
    }
}
