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
@Table(name = "artists")
public class Artist extends BaseEntity{
    private String name;
    private String description;
    private boolean isVerified;
    private int numberOfSongs;
    private int numberOfAlbum;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Album> albums;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Song> songs;

    @ManyToMany(mappedBy = "artist")
    private Set<Favorite> favorites;
}
