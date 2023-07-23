package com.atmosware.musicplayer.model.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "fallows")
public class Fallow extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id_fallower")
    private User userIdFollower;
    @ManyToOne()
    @JoinColumn(name = "user_id_fallowed")
    private User userIdFallowed;
}
