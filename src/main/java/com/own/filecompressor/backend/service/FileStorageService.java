package com.own.filecompressor.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService implements FileStorage {

    private Path rootLocation = null;

    @Autowired
    public FileStorageService(StorageProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new StorageException("File upload location can not be empty");
        }
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if(file.isEmpty()) {
                throw new StorageException("failed to store empty file");
            }
            //System.out.println("filename: " + file.getOriginalFilename());
            //System.out.println("root location: " + rootLocation.toString());
            //System.out.println("resolving rootlocation path with filename: " + rootLocation.resolve(Paths.get(file.getOriginalFilename())));
            //System.out.println("normilizing resolve: " + rootLocation.resolve(file.getOriginalFilename()).normalize());
            //System.out.println("using toAbsolutePath: " + rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath());
            Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!
            destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                    "Cannot store file outside current directory");
            } try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage.", e);
        }
    }
}
