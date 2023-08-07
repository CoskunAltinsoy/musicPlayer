package com.atmosware.musicplayer.service;

import com.atmosware.musicplayer.model.entity.Token;

import java.time.Instant;
import java.util.Optional;


public interface TokenService {
    public Token create(Long userId);
    public Token verifyExpiration(Token token);
    public int deleteByUserId(Long userId);
    public Optional<Token> findByToken(String token);
}
