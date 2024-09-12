package com.own.filecompressor.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    /*@PostMapping("/api/video")
    public String handleFileupload(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return file.getOriginalFilename();
    } */
}
