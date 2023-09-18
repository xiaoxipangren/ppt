package com.hermit.ppt.entity;

import com.hermit.ppt.converter.StringListConverter;
import com.hermit.ppt.enums.ArtType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "distro")
public class Distro extends BaseEntity{

    @Column
    private long size;

    @Column
    private String path;

    @Column
    private ArtType type;

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> snapshots;


    @ManyToOne
    @JoinColumn(name = "art_id")
    private Art art;

    @ManyToOne
    @JoinColumn(name = "torrent_id")
    private Torrent torrent;
}
