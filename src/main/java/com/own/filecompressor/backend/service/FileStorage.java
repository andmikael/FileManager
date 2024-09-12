package com.own.filecompressor.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
    void init();

    void store(MultipartFile file);
}
