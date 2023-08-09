package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.ArtistConverter;
import com.atmosware.musicplayer.dto.request.ArtistRequest;
import com.atmosware.musicplayer.dto.response.ArtistResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Image;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.model.enums.RoleType;
import com.atmosware.musicplayer.repository.ArtistRepository;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.ImageService;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository repository;
    @Lazy
    private final UserService userService;
    private final ImageService imageService;
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
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        artist.setId(0L);
        artist.setImage(image);
        artist.setCreatedDate(LocalDateTime.now());
        repository.save(artist);
        return new Result(Message.Artist.SUCCESSFUL);
    }

    @Override
    public Result update(ArtistRequest request, Long id) {
        checkIfArtistExistsById(id);
        Artist artist = repository.findById(id).orElseThrow();
        Image image = imageService.uploadImageToFileSystem(request.getFile()).getData();
        artist.setName(request.getName());
        artist.setImage(image);
        artist.setDescription(request.getDescription());
        artist.setUpdatedDate(LocalDateTime.now());
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
        byte[] byteImage = imageService.downloadImageFromFileSystem(artist.getImage().getFilePath());
        artistResponse.setImage(byteImage);
        return new DataResult<>(Message.Artist.SUCCESSFUL,artistResponse);
    }

    @Override
    public DataResult<List<ArtistResponse>> getAll() {
        List<Artist> artists = repository.findAll();
        var responses = artists
                .stream()
                .map(artist -> {
                    var artistResponse = converter.convertToResponse(artist);
                    byte[] byteImage = imageService.downloadImageFromFileSystem(artist.getImage().getFilePath());
                    artistResponse.setImage(byteImage);
                    return artistResponse;
                })
                .toList();
        return new DataResult<>(Message.Artist.SUCCESSFUL,responses);
    }

    @Override
    public DataResult<ArtistResponse> getByName(String name) {
        Artist artist = repository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new BusinessException(Message.Artist.NOT_EXIST));
        var artistResponse = converter.convertToResponse(artist);
        byte[] byteImage = imageService.downloadImageFromFileSystem(artist.getImage().getFilePath());
        artistResponse.setImage(byteImage);
        return new DataResult<ArtistResponse>(Message.Artist.SUCCESSFUL,artistResponse);
    }

    @Override
    public Artist findById(Long id) {
        checkIfArtistExistsById(id);
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException(Message.Artist.NOT_EXIST));
    }
    private void checkIfApprovalArtistTrue(User user){
        if (user.isApproveArtist() != true){
            throw new BusinessException(Message.Artist.NOT_APPROVED_YET);
        }
    }
    private void checkIfArtistExistsById(Long id){
        if (!repository.existsById(id)){
            throw new BusinessException(Message.Artist.NOT_EXIST);
        }
    }
}
