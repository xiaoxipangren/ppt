package com.hermit.ppt.entity;

import com.hermit.ppt.enums.Cup;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
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

    @ManyToMany(mappedBy = "artists")
    private List<Art> arts;

}
