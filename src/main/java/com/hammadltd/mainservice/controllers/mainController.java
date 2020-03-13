package com.hammadltd.mainservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {

    @RequestMapping("/")
    public String homePage() {
        String[] responseArray = new String[] {
                "Hey there! ",
                "Try this endpoint: ",
                "",
                "POST /info",
        };
        return String.join(" \n ", responseArray);
    }
}
