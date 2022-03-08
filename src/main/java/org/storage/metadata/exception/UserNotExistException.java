package org.storage.metadata.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("User not exists");
    }
}
