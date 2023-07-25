package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.*;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.TokenResetResponse;
import com.atmosware.musicplayer.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    public AuthResponse login(AuthRequest request);
    public void register(UserRequest request);
    public UserResponse getById(Long id);
    public List<UserResponse> getAll();
    public void delete(Long id);
    public void changePassword(PasswordRequest request);
    public void resetPassword(String token, TokenPasswordRequest request);
    public TokenResetResponse forgotPassword(ResetPasswordRequest request);
}
