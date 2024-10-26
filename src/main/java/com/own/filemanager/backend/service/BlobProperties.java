package com.own.filemanager.backend.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("blob")
public class BlobProperties {
    private String connectionStr = null;
    private String urlPrefix = null;
    

    public String getConnectionStr() {
        return this.connectionStr;
    }

    public void setConnectionStr() {
        // Grab azure key from env variable
        this.connectionStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
    }

    public String getUrlPrefix() {
        return this.urlPrefix;
    }

    public void setUrlPrefix() {
        this.urlPrefix = System.getenv("AZURE_STORAGE_URL_PREFIX");
    }
}
