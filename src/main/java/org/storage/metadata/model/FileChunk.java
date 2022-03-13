package org.storage.metadata.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "file_chunk")
public class FileChunk {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long chunk_id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "chunk_id")
    private Set<ChunkLocation> chunkLocations;

}
