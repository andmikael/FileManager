package com.own.filemanager.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.models.BlobContainerItem;
import com.own.filemanager.backend.service.BlobStorage;
import com.own.filemanager.backend.service.StorageFileNotFoundException;

@Controller
@CrossOrigin("http://localhost:8080")
public class ContainerController {
    private final BlobStorage blobStorage;

    @Autowired
    public ContainerController(BlobStorage blobStorage) {
        this.blobStorage = blobStorage;
    }

    @GetMapping("/")
    public String populateDropDown(Model model){
        
        List<String> blobs = new ArrayList<>();
        PagedIterable<BlobContainerItem> foundBlobs = blobStorage.getBlobContainers();
        for (BlobContainerItem elem : foundBlobs) {
            blobs.add(elem.getName());
        model.addAttribute("containers", blobs);
        }
        return "container";
    }

    @PostMapping(value="/")
    public String handleContainerSelection(@RequestBody String containerName, Model model) {
        containerName = containerName.substring(15, containerName.indexOf("\n")-1);
        blobStorage.createContainer(containerName);
        return "redirect:/index";
    }

    /*
     * TODO: add ability to delete a container
     */
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?>
    handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}