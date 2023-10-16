package com.hermit.ppt.entity;

import com.hermit.ppt.enums.Cup;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "artist")
public class Artist extends BaseEntity{

    @Column
    private String nick;

    @Column
    private LocalDate birth;

    @Column
    private Cup cup;

    @Column
    private int height;

    @Column
    private String avatar;

    @ManyToMany(targetEntity = Art.class,mappedBy = "artists",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Art> arts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist artist)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getName(), artist.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
