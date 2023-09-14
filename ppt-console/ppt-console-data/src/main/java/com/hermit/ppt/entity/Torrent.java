package com.hermit.ppt.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "torrent")
public class Torrent extends BaseEntity{

    @Column
    private String url;

    @Column
    private String hash;

    @Column
    private long size;

    @Column
    private String path;

    @OneToMany(mappedBy = "torrent",cascade = CascadeType.DETACH)
    private List<Art> arts;

}
