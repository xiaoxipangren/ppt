package com.hermit.ppt.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "series")
public class Series extends BaseEntity{

    @OneToMany(mappedBy = "series",cascade = CascadeType.DETACH)
    private List<Art> arts;

}
