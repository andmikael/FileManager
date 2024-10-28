package com.own.filemanager.backend.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;

public interface BlobStorage {
    void init();
    PagedIterable<BlobContainerItem> fetchBlobContainers();
    PagedIterable<BlobContainerItem> getBlobContainers();
    BlobContainerClient getContainerClient(String containerName);
    void setContainerClient(BlobContainerClient containerClient);
    String getContainerName();
    void createContainer(String containerName);
    BlobContainerClient getCurrentContainerClient();
    PagedIterable<BlobItem> getBlobs();
}
