package com.atmosware.musicplayer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "artists")
public class Artist extends BaseEntity {
    private String name;
    private String description;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    @ManyToMany(mappedBy = "followedArtists")
    private Set<User> followers = new HashSet<>();
}
