package com.own.filecompressor.backend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.own.filecompressor.backend.service.FileStorageService;
import com.own.filecompressor.backend.service.StorageProperties;

@Configuration
public class FileStorageConfig {
    
    @Bean
    @Primary
    public FileStorageService storageService(StorageProperties properties) {
        return new FileStorageService(properties);
    }
}
