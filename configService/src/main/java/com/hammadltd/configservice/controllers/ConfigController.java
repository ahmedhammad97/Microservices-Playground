package com.hammadltd.configservice.controllers;

import com.hammadltd.configservice.interfaces.IConfigFetcher;
import com.hammadltd.configservice.interfaces.ISecretValidator;
import com.hammadltd.configservice.models.ConfigRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class ConfigController {
    private ISecretValidator secretValidator;
    private IConfigFetcher configFetcher;

    public ConfigController(ISecretValidator secretValidator, IConfigFetcher configFetcher) {
        this.secretValidator = secretValidator;
        this.configFetcher = configFetcher;
    }

    @RequestMapping(value ="/configs", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getDummyConfigs(@RequestBody ConfigRequest configReq) {
        try {
            if (secretValidator.validate(configReq.getSecretKey())) {
                HashMap<String, String> neededConfigs = configFetcher.fetch(configReq.getNeededConfigs());
                return ResponseEntity.ok(new JSONObject(neededConfigs).toString());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
}
