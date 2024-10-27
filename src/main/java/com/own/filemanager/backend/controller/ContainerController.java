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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        PagedIterable<BlobContainerItem> foundBlobs = blobStorage.getBlobs();
        for (BlobContainerItem elem : foundBlobs) {
            blobs.add(elem.getName());
        model.addAttribute("containers", blobs);
        }
        return "container";
    }

    @PostMapping("/")
    public String handleContainerSelection(@RequestParam(name="selected-container")  String containerName,
    Model model) {
        blobStorage.setContainerClient(blobStorage.getContainerClient(containerName));
        return "redirect:/index";
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?>
    handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}