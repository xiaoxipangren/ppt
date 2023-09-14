package com.hermit.ppt.repository;

import com.hermit.ppt.entity.Art;
import com.hermit.ppt.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtRepository  extends JpaRepository<Art,Long> {
}
