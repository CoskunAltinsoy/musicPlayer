package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.model.entity.RefreshToken;

import java.util.Optional;


public interface RefreshTokenService {
    RefreshToken create(Long userId);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    void deleteByUserId(Long userId);
    Optional<RefreshToken> findByToken(String token);
}
