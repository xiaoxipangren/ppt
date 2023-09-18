package com.hermit.ppt.repository;

import com.hermit.ppt.entity.Label;
import com.hermit.ppt.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository  extends BaseRepository<Label> {
}
