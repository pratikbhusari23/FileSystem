package com.example.crud.controller;

import com.example.crud.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        try {
            return ResponseEntity.ok(fileService.listFiles());
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<String> readFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(fileService.readFile(filename));
        } catch (IOException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/{filename}")
    public ResponseEntity<Void> writeFile(@PathVariable String filename, @RequestBody String content) {
        try {
            fileService.writeFile(filename, content);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/upload/{filename}")
    public ResponseEntity<Void> uploadFile(@PathVariable String filename, @RequestParam("file") MultipartFile file) {
        try {
            fileService.uploadFile(filename, file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {
        try {
            fileService.deleteFile(filename);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
