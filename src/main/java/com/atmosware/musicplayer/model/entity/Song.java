package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "songs")
public class Song extends BaseEntity {
    private String name;
    private Short duration;
    private String lyrics;

    @ManyToOne()
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "song_genre",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.ALL)
    private Set<Playlist> playlists;

    @ManyToMany(mappedBy = "songs")
    private Set<Favorite> favorites;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;
}
