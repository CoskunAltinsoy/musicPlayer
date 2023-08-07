package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.dto.request.AuthRequest;
import com.atmosware.musicplayer.dto.request.RefreshTokenRequest;
import com.atmosware.musicplayer.dto.request.UserRequest;
import com.atmosware.musicplayer.dto.response.AuthResponse;
import com.atmosware.musicplayer.dto.response.RefreshTokenResponse;
import com.atmosware.musicplayer.util.result.DataResult;
import com.atmosware.musicplayer.util.result.Result;

public interface AuthService {
    DataResult<AuthResponse> login(AuthRequest request);
    Result register(UserRequest request);
    DataResult<RefreshTokenResponse> refreshToken(RefreshTokenRequest request);
    Result logout();
}
