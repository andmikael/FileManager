package com.own.filemanager.backend.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("blob")
public class BlobProperties {
    private String connectionStr = null;
    private String endpointString = null;
    

    public String getConnectionStr() {
        return this.connectionStr;
    }

    public void setConnectionStr() {
        // Grab azure key from env variable
        this.connectionStr = "DefaultEndpointsProtocol=https;AccountName=thisisnottaken;AccountKey=cS9lLDLb/BF6VS33Fm6Yxzl7kYxdtzc1k6WORFze4NiIeVJdeOkk0YzC0OPZDE02kP/lInPXi39T+AStXdEvGw==;EndpointSuffix=core.windows.net";
        //this.connectionStr = System.getenv("AZURE_STORAGE_CONNECTION_STRING");
    }

    public String getUrlPrefix() {
        return this.endpointString;
    }

    public void setUrlPrefix() {
        this.endpointString = "https://thisisnottaken.blob.core.windows.net/";
        //this.urlPrefix = System.getenv("AZURE_STORAGE_URL_ENDPOINT");
    }
}
