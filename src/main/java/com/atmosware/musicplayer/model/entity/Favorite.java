package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @ManyToOne()
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
