package com.hermit.ppt.repository;

import com.hermit.ppt.entity.Actress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActressRepository  extends JpaRepository<Actress,Long> {
}
