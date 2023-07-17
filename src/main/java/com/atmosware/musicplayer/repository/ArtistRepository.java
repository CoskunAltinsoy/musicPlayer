package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByNameIgnoreCase(String name);
}
