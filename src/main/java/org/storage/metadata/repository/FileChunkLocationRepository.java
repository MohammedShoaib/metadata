package org.storage.metadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.storage.metadata.model.ChunkLocation;

@Repository
public interface FileChunkLocationRepository extends JpaRepository<ChunkLocation, Long> {

}
