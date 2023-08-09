package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findByNameIgnoreCase(String name);

    List<Album> findByReleasedYearGreaterThan(LocalDateTime year);

    Optional<Boolean> existsByNameAndArtist_Id(String name, Long artistId);

    boolean existsByNameIgnoreCase(String name);

}
