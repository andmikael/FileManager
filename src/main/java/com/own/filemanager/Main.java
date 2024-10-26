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
        blobService.init();

        // Grab azure key from env variable
//        String connectStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");

// Azure SDK client builders accept the credential as a parameter
        //BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
        //.endpoint("https://thisisnottaken.blob.core.windows.net/")
        //.connectionString(connectStr)
        //.buildClient();

        // Create a unique name for the container
        //String containerName = "image-container";

        // Create or fetch container client object

        //PagedIterable<BlobContainerItem> listOfBlobs = blobServiceClient.listBlobContainers();

        //for (BlobContainerItem elem : listOfBlobs) {
        //    System.out.println(elem.getName());
        //}

        /*if(blobServiceClient.getBlobContainerClient(containerName) == null) {
            containerClient = blobServiceClient.createBlobContainer(containerName);
        } else {
            containerClient = blobServiceClient.getBlobContainerClient(containerName);
        }*/


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

        // List the blob(s) in the container.
        //for (BlobItem blobItem : containerClient.listBlobs()) {
        //    System.out.println("\t" + blobItem.getName());
        //}
    }
};