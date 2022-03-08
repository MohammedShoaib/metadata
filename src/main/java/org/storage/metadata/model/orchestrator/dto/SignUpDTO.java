package org.storage.metadata.model.orchestrator.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
