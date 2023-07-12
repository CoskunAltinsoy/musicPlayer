package com.atmosware.musicplayer.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime releasedYear;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs;
}
