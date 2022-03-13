package org.storage.metadata.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
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

    private boolean isDeleted = false;

    private boolean isDirectory = false;

    private Date dateCreated;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private List<FileChunk> chunks;
}
