package org.storage.metadata.model.orchestrator.dto;

import lombok.Data;

@Data
public class FileUploadDTO {
    String type;
    String namespace;
    Boolean isDeleted;
    Boolean isDirectory;
    String usernameOrEmail;
}
