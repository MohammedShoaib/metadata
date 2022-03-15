package org.storage.metadata.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.storage.metadata.model.ChunkLocation;
import org.storage.metadata.model.File;
import org.storage.metadata.model.FileChunk;
import org.storage.metadata.model.orchestrator.dto.FileChunkDTO;
import org.storage.metadata.repository.FileChunkRepository;
import org.storage.metadata.repository.FileRepository;
import org.storage.metadata.validator.FileValidator;

import java.util.List;

@Service
public class FileChunkService {
    private final FileValidator fileValidator;
    private final FileRepository fileRepository;
    private final FileChunkRepository fileChunkRepository;

    public FileChunkService(FileValidator fileValidator, FileRepository fileRepository, FileChunkRepository fileChunkRepository) {
        this.fileValidator = fileValidator;
        this.fileRepository = fileRepository;
        this.fileChunkRepository = fileChunkRepository;
    }

    public void insertChunkData(FileChunkDTO fileChunkDTO) {
        fileValidator.validateFileExists(fileChunkDTO.getFileId());
        createChunkRecord(fileChunkDTO);
    }

    @Transactional
    public void createChunkRecord(FileChunkDTO fileChunkDTO) {
        File file = getFileByID(fileChunkDTO.getFileId());

        FileChunk fileChunk = new FileChunk();
        fileChunk.setChunk_id(fileChunkDTO.getFileChunkId());

        ChunkLocation serverLocation = new ChunkLocation();
        serverLocation.setPath_to_chunk(fileChunkDTO.getFileChunkLocation());

        fileChunk.getChunkLocations().add(serverLocation);
        file.getChunks().add(fileChunk);

        fileRepository.saveAndFlush(file);
    }

    private File getFileByID(Long fileId) {
        fileRepository.findAll().forEach(Hibernate::initialize);
        return fileRepository.getById(fileId);
    }

    public List<FileChunk> getAllChunks(Long fileId) {
        File file = getFileByID(fileId);
        return file.getChunks();
    }

    public void deleteFileChunk(Long fileChunkId) {
        try {
            fileChunkRepository.deleteById(fileChunkId);
        } catch(Exception e) {
            throw e;
        }
    }
}
