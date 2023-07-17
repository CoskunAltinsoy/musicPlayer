package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class Album extends BaseEntity{
    private String name;
    private LocalDateTime releasedYear;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToMany(mappedBy = "albums")
    private Set<Favorite> favorites;
}
