package com.own.filecompressor.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.own.filecompressor.backend.service.FileStorage;
import com.own.filecompressor.backend.service.StorageFileNotFoundException;


@Controller
@CrossOrigin("http://localhost:8080")
public class FileController {

    private final FileStorage fileStorage;
    
    @Autowired
    public FileController(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @PostMapping("")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
    RedirectAttributes redirectAttributes) {
        fileStorage.store(file);
        redirectAttributes.addFlashAttribute("message", "uploaded " 
        + file.getOriginalFilename());

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?>
    handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}
