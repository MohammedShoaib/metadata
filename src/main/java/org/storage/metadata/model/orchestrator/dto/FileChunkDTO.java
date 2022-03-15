package org.storage.metadata.model.orchestrator.dto;

import lombok.Data;

@Data
public class FileChunkDTO {
    Long fileId;
    Long fileChunkId;
    String fileChunkLocation;
}
