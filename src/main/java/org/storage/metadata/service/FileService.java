package org.storage.metadata.service;

import org.springframework.stereotype.Service;
import org.storage.metadata.exception.UserNotExistException;
import org.storage.metadata.model.File;
import org.storage.metadata.model.User;
import org.storage.metadata.model.orchestrator.dto.FileUploadDTO;
import org.storage.metadata.repository.FileRepository;
import org.storage.metadata.repository.UserRepository;
import org.storage.metadata.validator.FileValidator;
import org.storage.metadata.validator.UserValidator;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final UserValidator userValidator;

    FileService(UserRepository userRepository, FileRepository fileRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.userValidator = userValidator;
    }
    public List<File> getUserFiles(String usernameOrEmail) {
        userValidator.validateUser(usernameOrEmail);
        Optional<User> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if(!user.isPresent()) {
            throw new UserNotExistException();
        }
        return user.get().getFiles();
    }

    public Long insertFileData(FileUploadDTO fileUploadDto) {
        userValidator.validateUser(fileUploadDto.getUsernameOrEmail());
        return createFileObject(fileUploadDto);
    }

    private Long createFileObject(FileUploadDTO fileUploadDto) {
        File file = new File();
        file.setDateCreated(System.currentTimeMillis());
        file.setType(fileUploadDto.getType());
        file.setNamespace(fileUploadDto.getNamespace());
        file.setIsDeleted(fileUploadDto.getIsDeleted());
        file.setIsDirectory(fileUploadDto.getIsDirectory());

        List<File> userFiles = getUserFiles(fileUploadDto.getUsernameOrEmail());
        userFiles.add(file);

        fileRepository.save(file);
        return file.getFile_id();
    }

    public void deleteFile(Long fileId) {
        try {
            fileRepository.deleteById(fileId);
        } catch (Exception e) {
            throw e;
        }
    }
}
