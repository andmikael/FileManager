package com.own.filemanager.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;

public class BlobStorageService implements BlobStorage {
    private PagedIterable<BlobContainerItem> listOfBlobs;
    private String connectionString = null;
    private String urlPrefix = null;
    private BlobServiceClient client = null;
    private BlobContainerClient containerClient = null;

    @Autowired
    public BlobStorageService(BlobProperties properties) {
        properties.setConnectionStr();
        this.connectionString = properties.getConnectionStr();
        properties.setUrlPrefix();
        this.urlPrefix = properties.getUrlPrefix();
    }

    @Override 
    public void init() {
        this.client = new BlobServiceClientBuilder()
        .endpoint(this.urlPrefix)
        .connectionString(this.connectionString)
        .buildClient();

        listOfBlobs = this.fetchBlobs();
    }

    @Override
    public PagedIterable<BlobContainerItem> fetchBlobs() {
        return client.listBlobContainers();
    }

    @Override
    public PagedIterable<BlobContainerItem> getBlobs() {
        return this.listOfBlobs;
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
}
