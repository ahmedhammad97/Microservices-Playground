package com.hammadltd.mainservice;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @RequestMapping(value ="/info", method = RequestMethod.POST)
    public String sayHi(@RequestBody Info info) {
        return "SAMO 3ALEEKO!";
    }
}
