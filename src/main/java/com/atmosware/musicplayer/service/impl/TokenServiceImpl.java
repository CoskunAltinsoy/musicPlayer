package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.Token;
import com.atmosware.musicplayer.repository.TokenRepository;
import com.atmosware.musicplayer.repository.UserRepository;
import com.atmosware.musicplayer.service.TokenService;
import com.atmosware.musicplayer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    private TokenRepository repository;
    private UserService userService;

    @Override
    public Token create(Long userId) {
        Token token = new Token();

        token.setUser(userService.findById(userId));
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        token.setToken(UUID.randomUUID().toString());

        token = repository.save(token);
        return token;
    }

    @Override
    public Token verifyExpiration(Token token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            repository.delete(token);
            throw new BusinessException("Refresh token was expired. Please make a new signin request");
        }
        return token;

        //todo: fix here
    }
    @Override
    public int deleteByUserId(Long userId) {
        return repository.deleteByUser(userService.findById(userId));
    }
    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token);
    }
}
