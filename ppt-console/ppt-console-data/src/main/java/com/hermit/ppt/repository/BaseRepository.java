package com.hermit.ppt.repository;

import com.hermit.ppt.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity>  extends JpaRepository<T,Long> {



}
