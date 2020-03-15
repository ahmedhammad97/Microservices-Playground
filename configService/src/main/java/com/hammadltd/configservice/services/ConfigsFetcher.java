package com.hammadltd.configservice.services;

import com.hammadltd.configservice.interfaces.IConfigFetcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigsFetcher implements IConfigFetcher {

    public HashMap<String, String> fetch(String[] neededConfigs) {
        return new HashMap<String, String>();
    }
}
