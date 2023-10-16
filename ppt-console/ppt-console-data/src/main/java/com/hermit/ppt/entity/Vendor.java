package com.hermit.ppt.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "vendor")
@Entity
public class Vendor extends BaseEntity{

    @Column
    private String logo;

    @Column
    private String slogan;

    @OneToMany(targetEntity = Art.class, mappedBy = "vendor",cascade = CascadeType.DETACH)
    private Set<Art> arts=new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor vendor)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getName(), vendor.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }
}
