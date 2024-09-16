package com.own.filecompressor;
import java.io.UncheckedIOException;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.own.filecompressor.backend.service.FileStorageService;
import com.own.filecompressor.backend.service.StorageProperties;



@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(StorageProperties.class)
public class Main {

    private static FileStorageService file;

    public Main(FileStorageService file) {
        this.file = file;
    }
    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        file.init();

        String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

// Azure SDK client builders accept the credential as a parameter
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
        .endpoint("https://thisisnottaken.blob.core.windows.net/")
        .connectionString(connectStr)
        .buildClient();

        // Create a unique name for the container
        String containerName = "testcontainer";

        // Create the container and return a container client object

        BlobContainerClient blobContainerClient = null;

        if(blobServiceClient.getBlobContainerClient(containerName) == null) {
            blobContainerClient = blobServiceClient.createBlobContainer(containerName);
        } else {
            blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
        }


        /*String fileName = "cat.jpg";
        String localPath = "C:\\Users\\user\\Desktop\\";
    
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName);*/

        /*System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());

        // Upload the blob
        try {
            blobClient.uploadFromFile(localPath + fileName);
            System.out.println("Upload from file succeeded");
        } catch (UncheckedIOException ex) {
            System.err.printf("Failed to upload from file %s%n", ex.getMessage());
        }*/

        System.out.println("\nListing blobs...");

        // List the blob(s) in the container.
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
        }
    }
};