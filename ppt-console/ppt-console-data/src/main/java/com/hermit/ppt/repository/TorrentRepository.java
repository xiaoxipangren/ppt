package com.hermit.ppt.repository;

import com.hermit.ppt.entity.Torrent;
import com.hermit.ppt.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorrentRepository extends BaseRepository<Torrent> {

    Torrent findByUrl(String url);

}
