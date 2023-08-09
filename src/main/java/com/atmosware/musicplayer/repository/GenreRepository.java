package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Boolean existsByNameIgnoreCase(String name);
}
