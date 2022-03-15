package org.storage.metadata.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long file_id;

    @NotBlank
    @Size(max = 10)
    private String type;

    @NotBlank
    private String namespace;

    private Boolean isDeleted;

    private Boolean isDirectory;

    // Epoch timestamp
    private Long dateCreated;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private List<FileChunk> chunks;
}
