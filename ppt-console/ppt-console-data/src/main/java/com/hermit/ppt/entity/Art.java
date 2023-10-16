package com.hermit.ppt.entity;

import com.hermit.ppt.converter.StringListConverter;
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
@Table(name = "art")
public class Art extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String code;

    @Column
    private LocalDate releaseDate;

    @Column
    private int last;

    @Column
    private String cover;

    @Column
    private String region;

    @ManyToOne(targetEntity = Vendor.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(targetEntity = Series.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "series_id")
    private Series series;

    @Column
    @Convert(converter = StringListConverter.class)
    private HashSet<String> tags;

    @ManyToMany(targetEntity =Label.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "art_label",
            joinColumns = {@JoinColumn(name = "art_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id",referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"art_id","label_id"}))
    private Set<Label> labels = new HashSet<>();

    @ManyToMany(targetEntity = Artist.class,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "art_artist",
            joinColumns = {@JoinColumn(name = "art_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id",referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"art_id","artist_id"}))
    private Set<Artist> artists = new HashSet<>();


    @OneToMany(targetEntity = Distro.class,mappedBy = "art")
    private Set<Distro> distros = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Art art)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getCode(), art.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode());
    }
}
