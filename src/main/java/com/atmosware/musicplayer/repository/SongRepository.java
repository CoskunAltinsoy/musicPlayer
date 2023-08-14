package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s " +
            "JOIN s.favorites f " +
            "GROUP BY s " +
            "ORDER BY COUNT(f) DESC")
    List<Song> findMostPopularByFavorites( );
    List<Song> findByAlbum_Artist_NameIgnoreCaseContaining(String name);
    List<Song> findByAlbum_NameIgnoreCaseContaining(String name);
    List<Song> findByGenres_NameIgnoreCaseContaining(String name);
}
