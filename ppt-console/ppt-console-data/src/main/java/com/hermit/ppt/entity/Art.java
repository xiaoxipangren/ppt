package com.hermit.ppt.entity;

import com.hermit.ppt.converter.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "art")
public class Art extends BaseEntity{

    @Column
    private String code;

    @Column
    private LocalDate releaseDate;

    @Column
    private int last;

    @Column
    private String cover;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> tags;

    @ManyToMany(mappedBy = "arts")
    private List<Label> labels;

    @ManyToMany(mappedBy = "arts")
    private List<Artist> artists;


    @OneToMany(mappedBy = "art",cascade = CascadeType.DETACH)
    private List<Distro> distros;

}
