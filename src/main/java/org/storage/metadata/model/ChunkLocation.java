package org.storage.metadata.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "chunk_location")
public class ChunkLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long chunk_location_id;

    @Column
    @NotNull
    private Long path_to_chunk;
}
