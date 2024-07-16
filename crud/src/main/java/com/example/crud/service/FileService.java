package com.example.crud.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private static final String DIRECTORY = "files/";

    public FileService() {
        try {
            Files.createDirectories(Paths.get(DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory!", e);
        }
    }

    public List<String> listFiles() throws IOException {
        try (var stream = Files.list(Paths.get(DIRECTORY))) {
            return stream.map(Path::getFileName)
                         .map(Path::toString)
                         .collect(Collectors.toList());
        }
    }

    public String readFile(String filename) throws IOException {
        return Files.readString(Paths.get(DIRECTORY + filename));
    }

    public void writeFile(String filename, String content) throws IOException {
        Files.writeString(Paths.get(DIRECTORY + filename), content);
    }

    public void uploadFile(String filename, MultipartFile file) throws IOException {
        Path path = Paths.get(DIRECTORY + filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteFile(String filename) throws IOException {
        Files.delete(Paths.get(DIRECTORY + filename));
    }
}
