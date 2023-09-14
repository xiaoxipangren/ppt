package com.hermit.ppt.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "label")
public class Label extends BaseEntity {


    @ManyToMany(mappedBy = "labels")
    private List<Art> arts;

}
