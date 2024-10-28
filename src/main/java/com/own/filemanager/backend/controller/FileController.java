package com.own.filemanager.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.models.BlobItem;
import com.own.filemanager.backend.service.BlobStorage;
import com.own.filemanager.backend.service.FileStorage;
import com.own.filemanager.backend.service.StorageFileNotFoundException;


@Controller
@CrossOrigin("http://localhost:8080")
public class FileController {

    private final FileStorage fileStorage;
    private final BlobStorage blobStorage;
    private PagedIterable<BlobItem> listOfBlobs;
    
    @Autowired
    public FileController(FileStorage fileStorage, BlobStorage blobStorage) {
        this.fileStorage = fileStorage;
        this.blobStorage = blobStorage;
    }

    @GetMapping("/index")
    public String switchControllers(Model model){
        if (blobStorage.getCurrentContainerClient() == null) {
            return "redirect:/";
        }
        model.addAttribute("container", blobStorage.getCurrentContainerClient().getBlobContainerName());
        this.listOfBlobs = blobStorage.getBlobs();
        model.addAttribute("blobs", listOfBlobs);
        return "index";
    }

    @PostMapping("/index")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    RedirectAttributes redirectAttributes) {
        fileStorage.store(file);
        redirectAttributes.addFlashAttribute("message", "uploaded " 
        + file.getOriginalFilename());

        return "redirect:/index";
    }

    /*
     * TODO: add ability to rename files before upload
     */

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?>
    handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}
