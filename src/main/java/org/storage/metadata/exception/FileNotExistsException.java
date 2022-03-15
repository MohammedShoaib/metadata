package org.storage.metadata.exception;

public class FileNotExistsException extends RuntimeException{
    public FileNotExistsException() {
        super("File requested doesn't exist");
    }
}
