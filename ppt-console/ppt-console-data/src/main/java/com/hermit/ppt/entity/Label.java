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
@Table(name = "label")
public class Label extends BaseEntity {


    @ManyToMany(targetEntity = Art.class,mappedBy = "labels",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<Art> arts = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label label)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getName(), label.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
