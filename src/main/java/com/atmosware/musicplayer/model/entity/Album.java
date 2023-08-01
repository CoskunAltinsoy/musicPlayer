package com.atmosware.musicplayer.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "albums")
public class Album extends BaseEntity {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime releasedYear;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
}
