package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "favorites")
public class Favorite extends BaseEntity {
    @ManyToMany
    @JoinTable(
            name = "favorite_song",
            joinColumns = @JoinColumn(name = "favorite_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> songs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
