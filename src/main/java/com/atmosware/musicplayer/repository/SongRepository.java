package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAlbum_Id(Long albumId);
    List<Song> findByArtist_Id(Long artistId);
}
