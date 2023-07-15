package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class Song extends BaseEntity{

    private String name;

    @ManyToOne()
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne()
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "song_genre",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "songs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Playlist> playlists;
}
