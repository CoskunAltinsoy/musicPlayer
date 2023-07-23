package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findByNameIgnoreCase(String name);

    List<Album> findByReleasedYearGreaterThan(LocalDateTime year);

    boolean existsByNameAndArtist_Id(String name, Long artistId);

    boolean existsByNameIgnoreCase(String name);

}
