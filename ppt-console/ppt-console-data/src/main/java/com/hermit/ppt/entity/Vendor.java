package com.hermit.ppt.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "vendor")
@Entity
public class Vendor extends BaseEntity{

    @Column
    private String logo;

    @Column
    private String slogan;

    @OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
    private List<Art> arts;
}
