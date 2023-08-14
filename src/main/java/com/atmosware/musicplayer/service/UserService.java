package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

import java.util.List;

public interface UserService {
    Result createDemandArtist(Long id);
    Result createApprovalArtist(Long id);
    DataResult<UserResponse> getById(Long id);
    DataResult<List<UserResponse>> getAll();
    Result delete(Long id);
    Result changePassword(PasswordRequest request);
    Result resetPassword(String token, TokenPasswordRequest request);
    DataResult<TokenResetResponse> forgotPassword(ResetPasswordRequest request);
    Result followUser(Long followerId, Long followedId);
    Result unfollowUser(Long followerId, Long followedId);
    User findById(Long id);
    User findByEmail(String email);
    User save(User user);
}
