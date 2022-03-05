package org.storage.metadata.worker.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"password"})
public class LoginDTO {
    private String usernameOrEmail;
    private String password;
}
