package org.storage.metadata.model;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "chunk_location")
public class ChunkLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chunk_location_id;

    @Column
    @NotNull
    private String path_to_chunk;
}
