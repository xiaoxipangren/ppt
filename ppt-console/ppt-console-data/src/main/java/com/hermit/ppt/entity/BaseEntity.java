package com.hermit.ppt.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Generated;
import org.springframework.data.annotation.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.persistence.AccessType;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@Access(AccessType.FIELD)
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @CreatedDate
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(name = "create_by", nullable = false, updatable = false)
    private String createBy;

    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    @LastModifiedBy
    @Column(name = "update_by", nullable = false)
    private String updateBy;

    @Column
    private String name;

    @Column
    private String description;
}
