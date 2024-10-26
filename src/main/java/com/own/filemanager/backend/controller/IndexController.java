package com.own.filemanager.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("")
    public String blob() {
        return "container";
    }

    @RequestMapping("index")
    public String storage() {
        return "index";
    }
}
