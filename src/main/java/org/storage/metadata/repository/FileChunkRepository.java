package org.storage.metadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.storage.metadata.model.FileChunk;

@Repository
public interface FileChunkRepository extends JpaRepository<FileChunk, Long> {

}
