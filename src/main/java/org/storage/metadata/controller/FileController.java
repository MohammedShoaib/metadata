package org.storage.metadata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.storage.metadata.model.File;
import org.storage.metadata.model.orchestrator.dto.FileUploadDTO;
import org.storage.metadata.service.FileService;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private FileService fileService;

    FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(path = "/all/{username}")
    public ResponseEntity<List<File>> getUserFiles(@PathVariable("username") final String usernameOrEmail) {
        return new ResponseEntity<>(fileService.getUserFiles(usernameOrEmail), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createFileMetadata(@RequestBody FileUploadDTO fileUploadDto){
        Long fileId = fileService.insertFileData(fileUploadDto);
        return new ResponseEntity<>(fileId, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{fileId}")
    public ResponseEntity<?> deleteFileMetadata(@PathVariable("fileId") final Long fileId){
        fileService.deleteFile(fileId);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
