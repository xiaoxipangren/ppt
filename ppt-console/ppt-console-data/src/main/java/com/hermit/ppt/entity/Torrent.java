package com.hermit.ppt.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "torrent")
public class Torrent extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String url;

    @Column
    private String hash;

    @Column
    private long size;

    @Column
    private boolean half;

    @Column
    private String path;

    @Column
    private boolean free;

    @Column
    private boolean pack;

    @Column
    private int seedings;

    @Column
    private String cover;

    @Column
    private int downloadings;

    @Column
    private String type;

    @Column
    private int downloadeds;

    @Column
    private boolean downloaded;

    @Column
    private LocalDateTime uploadTime;

    @Column
    private LocalDateTime freeEnd;

    @OneToMany(targetEntity = Distro.class,mappedBy = "torrent")
    private Set<Distro> distros = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Torrent torrent)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getHash(), torrent.getHash());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getHash());
    }
}
