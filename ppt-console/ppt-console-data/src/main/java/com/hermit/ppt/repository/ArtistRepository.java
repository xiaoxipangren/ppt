package com.hermit.ppt.repository;

import com.hermit.ppt.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends BaseRepository<Artist> {
}
