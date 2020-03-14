package com.hammadltd.mainservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/")
    public String homePage() {
        return "Hey there! Try this endpoint: POST /info";
    }
}
