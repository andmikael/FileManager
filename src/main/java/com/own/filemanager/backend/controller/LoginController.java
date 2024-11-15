package com.own.filemanager.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.models.BlobContainerItem;
import com.own.filemanager.backend.service.BlobStorage;

@Controller
@CrossOrigin("http://localhost:8080")
public class LoginController {
    private final BlobStorage blobStorage;

    @Autowired
    public LoginController(BlobStorage blobStorage) {
        this.blobStorage = blobStorage;
    }

        @GetMapping("/")
    public String initPage(Model model){
        return "login";
    }

    @PostMapping(value="/loginContainer")
    public String handleLogin(@RequestBody String postBody, Model model) {
        String connectionString = postBody.substring(15, postBody.indexOf("\n")-1);
        this.blobStorage.setConnString(connectionString);
        return "redirect:/container";
    }
}
