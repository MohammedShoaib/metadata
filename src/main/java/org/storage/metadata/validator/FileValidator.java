package org.storage.metadata.validator;

import org.springframework.stereotype.Service;
import org.storage.metadata.exception.FileNotExistsException;
import org.storage.metadata.repository.FileRepository;

@Service
public class FileValidator {
    private final FileRepository fileRepository;

    public FileValidator(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void validateFileExists(Long fileId) {
        if(!fileRepository.existsById(fileId)) {
            throw new FileNotExistsException();
        }
    }
}
