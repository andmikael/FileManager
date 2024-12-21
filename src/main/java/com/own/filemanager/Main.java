package com.own.filemanager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.own.filemanager.backend.service.BlobStorageService;
import com.own.filemanager.backend.service.FileStorageService;



@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
public class Main {

    private static FileStorageService file;
    private static BlobStorageService blobService;

    public Main(FileStorageService file, BlobStorageService blobService) {
        Main.file = file;
        Main.blobService = blobService;
    }
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        file.init();
    }
};