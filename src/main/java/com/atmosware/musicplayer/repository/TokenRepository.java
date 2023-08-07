package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Token;
import com.atmosware.musicplayer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    @Modifying
    int deleteByUser(User user);
}
