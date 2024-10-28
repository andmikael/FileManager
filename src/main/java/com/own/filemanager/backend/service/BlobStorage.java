package com.own.filemanager.backend.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobContainerItem;

public interface BlobStorage {
    void init();
    PagedIterable<BlobContainerItem> fetchBlobs();
    PagedIterable<BlobContainerItem> getBlobs();
    BlobContainerClient getContainerClient(String containerName);
    void setContainerClient(BlobContainerClient containerClient);
    String getContainerName();
    void createContainer(String containerName);
    BlobContainerClient getCurrentContainerClient();
}
