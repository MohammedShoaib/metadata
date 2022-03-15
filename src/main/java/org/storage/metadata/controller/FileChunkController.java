package org.storage.metadata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.storage.metadata.model.FileChunk;
import org.storage.metadata.model.orchestrator.dto.FileChunkDTO;
import org.storage.metadata.service.FileChunkService;
import org.storage.metadata.validator.FileValidator;

import java.util.List;

@RestController
@RequestMapping("/fileChunk")
public class FileChunkController {
    private final FileChunkService fileChunkService;

    public FileChunkController(FileChunkService fileChunkService, FileValidator fileValidator) {
        this.fileChunkService = fileChunkService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChunk(@RequestBody FileChunkDTO fileChunkDTO){
        fileChunkService.insertChunkData(fileChunkDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/all/{fileId}")
    public ResponseEntity<List<FileChunk>> getChunkData(@PathVariable("fileId") final Long fileId) {
        return new ResponseEntity<>(fileChunkService.getAllChunks(fileId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{fileChunkId}")
    public ResponseEntity<?> deleteChunk(@PathVariable("fileChunkId") final Long fileChunkId) {
        fileChunkService.deleteFileChunk(fileChunkId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
