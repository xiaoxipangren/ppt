package com.hermit.ppt.entity;

import com.hermit.ppt.converter.StringListConverter;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "distro")
public class Distro extends BaseEntity{

    @Column
    private long size;

    @Column
    private String filename;

    @Column
    private String title;

    @Column
    private boolean censored=true;

    @Column
    private String resolution;

    @Column
    private String codec;

    @Column
    @Convert(converter = StringListConverter.class)
    private Set<String> snapshots = new HashSet<>();

    @ManyToOne(targetEntity = Art.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "art_id",referencedColumnName = "id")
    private Art art;

    @ManyToOne(targetEntity = Torrent.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "torrent_id",referencedColumnName = "id")
    private Torrent torrent;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distro distro)) return false;
        if (!super.equals(o)) return false;
        return getSize() == distro.getSize() && isCensored() == distro.isCensored() && Objects.equals(getName(), distro.getName()) && Objects.equals(getResolution(), distro.getResolution()) && Objects.equals(getCodec(), distro.getCodec());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),getName(), getSize(), isCensored(), getResolution(), getCodec());
    }
}
