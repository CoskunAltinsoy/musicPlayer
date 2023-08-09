package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.RoleConverter;
import com.atmosware.musicplayer.converter.UserConverter;
import com.atmosware.musicplayer.dto.request.PasswordRequest;
import com.atmosware.musicplayer.dto.request.ResetPasswordRequest;
import com.atmosware.musicplayer.dto.request.TokenPasswordRequest;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.UserRepository;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.RequestUtils;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.email.EmailService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.token.PasswordTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserConverter converter;
    private final PasswordTokenGenerator generator;
    private final RoleService roleService;
    private final ArtistService artistService;
    private final RoleConverter roleConverter;
    private final EmailService emailService;

    @Override
    public Result createDemandArtist(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        checkUserDemandStateTrue(user.isDemandArtist());
        user.setDemandArtist(true);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result createApprovalArtist(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        checkUserApprovalAndDemandStateTrue(user.isApproveArtist());
        checkUserDemandStateForApprovalFalse(user.isDemandArtist());
        user.setApproveArtist(true);
        Set<Role> roles = setRoleIdsFromUser(user);
        user.setRoles(roles);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public DataResult<UserResponse> getById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        Set<RoleResponse> roleResponses = roleConverter.convertToResponseList(user.getRoles());
        UserResponse userResponse = converter.convertToResponse(user);
        userResponse.setRoles(roleResponses);
        return new DataResult<>(Message.User.SUCCESSFUL, userResponse);
    }

    @Override
    public DataResult<List<UserResponse>> getAll() {
        List<User> users = repository.findAll();
        var responses = users
                .stream()
                .map(user -> {
                    Set<RoleResponse> roleResponses = roleConverter.convertToResponseList(user.getRoles());
                    UserResponse userResponse = converter.convertToResponse(user);
                    userResponse.setRoles(roleResponses);
                    return userResponse;
                })
                .toList();
        return new DataResult<>(Message.User.SUCCESSFUL, responses);
    }

    @Override
    public Result delete(Long id) {
        checkIfUserExistsById(id);
        repository.deleteById(id);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result changePassword(PasswordRequest request) {
        checkIfPasswordMatches(request.getEmail(), request.getOldPassword(), request.getNewPassword());
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result resetPassword(String token, TokenPasswordRequest request) {
        User user = repository.findByResetPasswordToken(token).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setResetPasswordToken(null);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public DataResult<TokenResetResponse> forgotPassword(ResetPasswordRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        user.setResetPasswordToken(generator.generateToken());
        repository.save(user);
        TokenResetResponse tokenResetResponse = new TokenResetResponse();
        tokenResetResponse.setUrlWithToken(RequestUtils.getClientIPAddress() + "/api/users/resetPassword?token=" +
                user.getResetPasswordToken());
        emailService.sendEmail(user.getEmail(), tokenResetResponse.getUrlWithToken());
        return new DataResult<>(Message.User.SUCCESSFUL, tokenResetResponse);
    }

    @Override
    public Result followUser(Long followerId, Long followedId) {
        checkIfUserExistsById(followedId);
        User followerUser = repository.findById(followerId).orElseThrow();
        User followedUser = repository.findById(followedId).orElseThrow();
        followerUser.getFollowed().add(followedUser);
        repository.save(followerUser);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result unfollowUser(Long followerId, Long followedId) {
        checkIfUserExistsById(followedId);
        User followerUser = repository.findById(followerId)
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        User followedUser = repository.findById(followedId)
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        followerUser.getFollowed().remove(followedUser);
        repository.save(followerUser);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result followArtist(Long followerId, Long followedArtistId) {
        User user = repository.findById(followerId)
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        Artist artist = artistService.findById(followedArtistId);
        user.getFollowedArtists().add(artist);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public Result unfollowArtist(Long followerId, Long followedArtistId) {
        User user = repository.findById(followerId)
                .orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        Artist artist = artistService.findById(followedArtistId);
        user.getFollowedArtists().remove(artist);
        repository.save(user);
        return new Result(Message.User.SUCCESSFUL);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    private Set<Role> setRoleIdsFromUser(User user) {
        return user.getRoles().stream()
                .map(role -> {
                    return roleService.findById(role.getId());
                })
                .collect(Collectors.toSet());
    }

    private void checkIfPasswordMatches(String email, String oldPassword, String newPassword) {
        var user = repository.findByEmail(email).orElseThrow(() -> new BusinessException(Message.User.NOT_EXIST));
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(Message.User.INCORRECT_PASSWORD);
        }
    }

    private void checkUserDemandStateTrue(boolean demand) {
        if (demand == true) {
            throw new BusinessException(Message.User.DEMAND_CREATED);
        }
    }

    private void checkUserApprovalAndDemandStateTrue(boolean approval) {
        if (approval == true) {
            throw new BusinessException(Message.User.APPROVAL_CREATED);
        }
    }

    private void checkUserDemandStateForApprovalFalse(boolean demand) {
        if (demand == false) {
            throw new BusinessException(Message.User.MUST_CREATE_DEMAND);
        }
    }

    private void checkIfUserExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.User.NOT_EXIST);
        }
    }

    private void checkIfUserExistsByEmail(String email) {
        if (!repository.existsByEmail(email)) {
            throw new BusinessException(Message.User.NOT_EXIST);
        }
    }
}
