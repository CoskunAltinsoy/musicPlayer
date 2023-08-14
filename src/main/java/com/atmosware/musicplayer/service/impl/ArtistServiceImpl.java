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
import com.atmosware.musicplayer.util.TimeUtil;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository repository;
    @Lazy
    private final UserService userService;
    private final ArtistConverter converter;
    private final RoleService roleService;
    private final AuthenticationFacade authenticationFacade;
    private final TimeUtil timeUtil;

    @Override
    public Result create(ArtistRequest request) {
        User user = userService.findByEmail(authenticationFacade.getUsername());
        checkIfApprovalArtistTrue(user);
        Role role = roleService.findByName(RoleType.ARTIST);
        user.getRoles().add(role);
        Artist artist = converter.convertToEntity(request);
        artist.setCreatedDate(timeUtil.getLocalDateTimeNow());
        repository.save(artist);
        return new Result(Message.Artist.SUCCESSFUL);
    }

    @Override
    public Result update(ArtistRequest request, Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        artist.setName(request.getName());
        artist.setDescription(request.getDescription());
        artist.setUpdatedDate(timeUtil.getLocalDateTimeNow());
        repository.save(artist);
        return new Result(Message.Artist.SUCCESSFUL);
    }

    @Override
    public Result delete(Long id) {
        checkIfArtistExistsById(id);
        repository.deleteById(id);
        return new Result(Message.Artist.SUCCESSFUL);
    }

    @Override
    public DataResult<ArtistResponse> getById(Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        var artistResponse = converter.convertToResponse(artist);
        return new DataResult<>(Message.Artist.SUCCESSFUL, artistResponse);
    }

    @Override
    public DataResult<List<ArtistResponse>> getAll() {
        List<Artist> artists = repository.findAll();
        var responses = artists
                .stream()
                .map(artist -> {
                    var artistResponse = converter.convertToResponse(artist);
                    return artistResponse;
                })
                .toList();
        return new DataResult<>(Message.Artist.SUCCESSFUL, responses);
    }

    @Override
    public Result followArtist(Long followerId, Long followedArtistId) {
        User user = userService.findById(followerId);
        Artist artist = repository.findById(followedArtistId)
                .orElseThrow(() -> new BusinessException(Message.Artist.SUCCESSFUL));
        user.getFollowedArtists().add(artist);
        userService.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result unfollowArtist(Long followerId, Long followedArtistId) {
        User user = userService.findById(followerId);
        Artist artist = repository.findById(followedArtistId)
                .orElseThrow(() -> new BusinessException(Message.Artist.SUCCESSFUL));
        user.getFollowedArtists().add(artist);
        userService.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public DataResult<ArtistResponse> getByName(String name) {
        Artist artist = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BusinessException(Message.Artist.NOT_EXIST));
        var artistResponse = converter.convertToResponse(artist);
        return new DataResult<ArtistResponse>(Message.Artist.SUCCESSFUL, artistResponse);
    }

    @Override
    public Artist findById(Long id) {
        checkIfArtistExistsById(id);
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Message.Artist.NOT_EXIST));
    }

    private void checkIfApprovalArtistTrue(User user) {
        if (user.isApproveArtist() != true || user.getRoles().contains("ADMIN")) {
            throw new BusinessException(Message.Artist.NOT_APPROVED_YET);
        }
    }

    private void checkIfArtistExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.Artist.NOT_EXIST);
        }
    }
}
