package com.hermit.ppt.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column
    private boolean free;

    @Column
    private int seeds;

    @Column
    private String cover;

    @Column
    private int downloadings;

    @Column
    private int downloadeds;

    @Column
    private LocalDateTime uploadTime;

    @Column
    private LocalDateTime freeEnd;

    @OneToMany(mappedBy = "torrent",cascade = CascadeType.DETACH)
    private List<Distro> distros;

}
