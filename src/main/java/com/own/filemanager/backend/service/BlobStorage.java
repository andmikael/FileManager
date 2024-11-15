package com.own.filemanager.backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;

public interface BlobStorage {
    void init();
    public void setConnString(String connString);
    PagedIterable<BlobContainerItem> fetchBlobContainers();
    PagedIterable<BlobContainerItem> getBlobContainers();
    BlobContainerClient getContainerClient(String containerName);
    void setContainerClient(BlobContainerClient containerClient);
    String getContainerName();
    void createContainer(String containerName);
    BlobContainerClient getCurrentContainerClient();
    PagedIterable<BlobItem> getBlobs();
    Boolean deleteContainer();
    Boolean uploadFile(MultipartFile file, String filename);
}
