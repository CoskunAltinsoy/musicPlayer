package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.converter.RoleConverter;
import com.atmosware.musicplayer.converter.UserConverter;
import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.RoleResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Artist;
import com.atmosware.musicplayer.model.entity.Role;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.UserRepository;
import com.atmosware.musicplayer.security.CustomUserDetail;
import com.atmosware.musicplayer.security.JwtUtils;
import com.atmosware.musicplayer.service.ArtistService;
import com.atmosware.musicplayer.service.RoleService;
import com.atmosware.musicplayer.service.UserService;
import com.atmosware.musicplayer.util.constant.Message;
import com.atmosware.musicplayer.util.email.EmailService;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;
import com.atmosware.musicplayer.util.security.token.PasswordTokenGenerator;
import io.netty.handler.codec.MessageAggregationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserConverter converter;
    private final PasswordTokenGenerator generator;
    private final RoleService roleService;
    private final ArtistService artistService;
    private final RoleConverter roleConverter;
    private final EmailService emailService;

    @Override
    public DataResult<AuthResponse> login(AuthRequest request) {
        checkIfUserExistsByEmail(request.getEmail());
        Authentication authentication =
                authenticationManager.authenticate
                        (new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Set<String> roles = customUserDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return new DataResult<AuthResponse>(Message.User.successful,
                new AuthResponse(jwt, customUserDetail.getUsername(), roles));
    }

    @Override
    public DataResult register(UserRequest request) {
        User user = converter.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = setRoleIdsFromRequest(request);
        user.setDemandArtist(false);
        user.setApproveArtist(false);
        user.setRoles(roles);
        var userSaved = repository.save(user);
        return new DataResult(Message.User.successful, userSaved);
    }

    @Override
    public Result createDemandArtist(Long id) {
        User user = repository.findById(id).orElseThrow();
        checkUserDemandState(user.isDemandArtist());
        user.setDemandArtist(true);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public Result createApprovalArtist(Long id) {
        User user = repository.findById(id).orElseThrow();
        checkUserApprovalAndDemandState(user.isApproveArtist());
        checkUserDemandStateForApproval(user.isDemandArtist());
        user.setApproveArtist(true);
        Set<Role> roles = setRoleIdsFromUser(user);
        user.setRoles(roles);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public DataResult<UserResponse> getById(Long id) {
        checkIfUserExistsById(id);
        User user = repository.findById(id).orElseThrow();
        Set<RoleResponse> roleResponses = roleConverter.convertToResponseList(user.getRoles());
        UserResponse userResponse = converter.convertToResponse(user);
        userResponse.setRoles(roleResponses);
        return new DataResult<UserResponse>(Message.User.successful, userResponse);
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
        return new DataResult<List<UserResponse>>(Message.User.successful, responses);
    }

    @Override
    public Result delete(Long id) {
        checkIfUserExistsById(id);
        repository.deleteById(id);
        return new Result(Message.User.successful);
    }

    @Override
    public Result changePassword(PasswordRequest request) {
        checkIfUserExistsByEmail(request.getEmail());
        checkIfPasswordMatches(request.getEmail(), request.getOldPassword(), request.getNewPassword());
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedNewPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
        user.setPassword(encodedNewPassword);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public Result resetPassword(String token, TokenPasswordRequest request) {
        User user = repository.findByResetPasswordToken(token).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setResetPasswordToken(null);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public DataResult<TokenResetResponse> forgotPassword(ResetPasswordRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow();
        user.setResetPasswordToken(generator.generateToken());
        repository.save(user);
        TokenResetResponse tokenResetResponse = new TokenResetResponse();
        tokenResetResponse.setUrlWithToken("http://localhost:8080/api/users/resetPassword?token=" +
                user.getResetPasswordToken());
        emailService.sendEmail(user.getEmail(), tokenResetResponse.getUrlWithToken());
        return new DataResult<TokenResetResponse>(Message.User.successful,tokenResetResponse);
    }

    @Override
    public Result followUser(Long followerId, Long followedId) {
        checkIfUserExistsById(followedId);
        User followerUser = repository.findById(followedId).orElseThrow();
        User followedUser = repository.findById(followedId).orElseThrow();
        followerUser.getFollowed().add(followedUser);
        repository.save(followerUser);
        return new Result(Message.User.successful);
    }

    @Override
    public Result unfollowUser(Long followerId, Long followedId) {
        checkIfUserExistsById(followedId);
        User followerUser = repository.findById(followedId).orElseThrow();
        User followedUser = repository.findById(followedId).orElseThrow();
        followerUser.getFollowed().remove(followedUser);
        repository.save(followerUser);
        return new Result(Message.User.successful);
    }

    @Override
    public Result followArtist(Long followerId, Long followedArtistId) {
        User user = repository.findById(followerId).orElseThrow();
        Artist artist = artistService.findById(followedArtistId);
        user.getFollowedArtists().add(artist);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public Result unfollowArtist(Long followerId, Long followedArtistId) {
        User user = repository.findById(followerId).orElseThrow();
        Artist artist = artistService.findById(followedArtistId);
        user.getFollowedArtists().remove(artist);
        repository.save(user);
        return new Result(Message.User.successful);
    }

    @Override
    public User findById(Long id) {
        checkIfUserExistsById(id);
        return repository.findById(id).orElseThrow();
    }

    @Override
    public User findByEmail(String email) {
        checkIfUserExistsByEmail(email);
        return repository.findByEmail(email).orElseThrow();
    }

    private Set<Role> setRoleIdsFromRequest(UserRequest request) {
        return request.getRoleIds().stream()
                .map(roleId -> {
                    return roleService.findById(roleId);
                })
                .collect(Collectors.toSet());
    }

    private Set<Role> setRoleIdsFromUser(User user) {
        return user.getRoles().stream()
                .map(role -> {
                    return roleService.findById(role.getId());
                })
                .collect(Collectors.toSet());
    }

    private void checkIfPasswordMatches(String email, String oldPassword, String newPassword) {
        var user = repository.findByEmail(email).orElseThrow();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(Message.User.incorrectPassword);
        }
    }

    private void checkUserDemandState(boolean demand) {
        if (demand == true) {
            throw new BusinessException(Message.User.demandCreated);
        }
    }

    private void checkUserApprovalAndDemandState(boolean approval) {
        if (approval == true) {
            throw new BusinessException(Message.User.approvalCreated);
        }
    }

    private void checkUserDemandStateForApproval(boolean demand) {
        if (demand == false) {
            throw new BusinessException(Message.User.mustCreateDemand);
        }
    }

    private void checkIfUserExistsById(Long id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Message.User.notExist);
        }
    }

    private void checkIfUserExistsByEmail(String email) {
        if (!repository.existsByEmail(email)) {
            throw new BusinessException(Message.User.notExist);
        }
    }
}
