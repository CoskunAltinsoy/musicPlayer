package com.atmosware.musicplayer.service.impl;

import com.atmosware.musicplayer.exception.BusinessException;
import com.atmosware.musicplayer.model.entity.RefreshToken;
import com.atmosware.musicplayer.model.entity.User;
import com.atmosware.musicplayer.repository.RefreshTokenRepository;
import com.atmosware.musicplayer.service.RefreshTokenService;
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
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository repository;
    @Autowired
    private UserService userService;

    @Override
    public RefreshToken create(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userService.findById(userId);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        repository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            repository.delete(refreshToken);
            throw new BusinessException("Refresh refreshToken was expired. Please make a new signin request");
        }
        return refreshToken;

        //todo: fix here
    }
    @Override
    public void deleteByUserId(Long userId) {
        repository.deleteByUserId(userId);
    }
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }
}
