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

    @GetMapping("/container")
    public String populateDropDown(Model model){
        this.blobStorage.init();
        List<String> blobs = new ArrayList<>();
        PagedIterable<BlobContainerItem> foundBlobs = blobStorage.getBlobContainers();
        for (BlobContainerItem elem : foundBlobs) {
            blobs.add(elem.getName());
        model.addAttribute("containers", blobs);
        }
        return "container";
    }

    @PostMapping(value="/selectContainer")
    public String handleContainerSelection(@RequestBody String postBody, Model model) {
        String containerName = postBody.substring(15, postBody.indexOf("\n")-1);
        String method = postBody.substring(postBody.indexOf("\n"), postBody.indexOf("=", postBody.indexOf("\n")));
        method = method.trim().toLowerCase();
        System.out.println(method);
        if (method.equals("delete-button")) {
            blobStorage.createContainer(containerName);
            blobStorage.setContainerClient(blobStorage.getContainerClient(containerName));
            if (blobStorage.deleteContainer()) {
                System.out.println("deletion successful");
                return "redirect:/";
            } else {
                System.out.println("deletion unsuccessful");
                return "redirect:/Error";
            }
        }

        blobStorage.createContainer(containerName); // createContainer method returns a handle to an existing container if it already exists
        return "redirect:/index";
    }

    @PostMapping(value="/createContainer")
    public String handleContainerCreation(@RequestBody String postBody, Model model) {
        String containerName = postBody.substring(15, postBody.indexOf("\n")-1);
        blobStorage.createContainer(containerName);
        return "redirect:/index";
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?>
    handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}