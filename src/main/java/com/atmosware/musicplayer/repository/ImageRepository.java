package com.atmosware.musicplayer.repository;

import com.atmosware.musicplayer.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String name);
    Optional<Image> findById(Long id);
}
