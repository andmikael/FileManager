package com.own.filemanager.backend.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;

public class BlobStorageService implements BlobStorage {
    private PagedIterable<BlobContainerItem> listOfBlobContainers;
    private String connectionString = null;
    private String urlPrefix = null;
    private BlobServiceClient client = null;
    private BlobContainerClient containerClient = null;
    private PagedIterable<BlobItem> listOfBlobs;

    @Autowired
    public BlobStorageService(BlobProperties properties) {
        //properties.setConnectionStr(connString);
        this.connectionString = properties.getConnectionStr();
        properties.setUrlPrefix();
        this.urlPrefix = properties.getUrlPrefix();
    }

    @Override
    public void setConnString(String connString) {
        this.connectionString = connString;
    }

    @Override 
    public void init() {
        this.client = new BlobServiceClientBuilder()
        .endpoint(this.urlPrefix)
        .connectionString(this.connectionString)
        .buildClient();

        listOfBlobContainers = this.fetchBlobContainers();
    }

    @Override
    public PagedIterable<BlobContainerItem> fetchBlobContainers() {
        return client.listBlobContainers();
    }

    @Override
    public PagedIterable<BlobContainerItem> getBlobContainers() {
        return this.listOfBlobContainers;
    }

    @Override
    public BlobContainerClient getContainerClient(String containerName) {
        return this.client.getBlobContainerClient(containerName);
    }

    @Override
    public void setContainerClient(BlobContainerClient containerClient) {
        this.containerClient = containerClient;
    }

    @Override
    public String getContainerName() {
        return this.containerClient.getBlobContainerName();
    }

    @Override
    public void createContainer(String containerName) {
        this.containerClient = client.createBlobContainerIfNotExists(containerName);
    }

    @Override
    public BlobContainerClient getCurrentContainerClient() {
        return this.containerClient;
    }

    @Override
    public PagedIterable<BlobItem> getBlobs() {
        return this.containerClient.listBlobs();
    }

    @Override
    public Boolean deleteContainer() {
        return containerClient.deleteIfExists();
    }

    @Override
    public Boolean uploadFile(MultipartFile file, String filename) {
        BlobClient blobClient  = this.containerClient.getBlobClient(filename);
        InputStream fileStream = null;
        try {
            fileStream = file.getInputStream();
        } catch (IOException ex) {
        }
        blobClient.upload(fileStream);
        return true;
    }
}
