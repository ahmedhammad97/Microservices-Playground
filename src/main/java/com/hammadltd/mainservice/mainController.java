package com.hammadltd.mainservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mainController {

    @RequestMapping("/")
    public String homePage() {
        String[] responseArray = new String[] {
                "Hey there!",
                "We offer few endpoints to send us your data",
                "",
                "POST /info",
        };
        return String.join("\n", responseArray);
    }
}
