package com.hermit.ppt.entity;

import com.hermit.ppt.converter.StringListConverter;
import com.hermit.ppt.enums.Cup;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "actress")
public class Actress extends BaseEntity{

    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> nicks;

    @Column
    private LocalDate birth;

    @Column
    private Cup cup;

    @Column
    private int height;

    @Column
    private String avatar;

    @ManyToMany(mappedBy = "actresses")
    private List<Art> arts;

}
