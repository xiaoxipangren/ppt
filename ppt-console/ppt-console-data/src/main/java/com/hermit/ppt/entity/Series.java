package com.hermit.ppt.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "series")
public class Series extends BaseEntity{

    @OneToMany(targetEntity = Art.class,mappedBy = "series",cascade = CascadeType.DETACH)
    private Set<Art> arts=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Series series)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getName(), series.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
