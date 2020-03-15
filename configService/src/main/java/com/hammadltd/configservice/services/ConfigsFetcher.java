package com.hammadltd.configservice.services;

import com.hammadltd.configservice.interfaces.IConfigFetcher;
import com.hammadltd.configservice.interfaces.IConfigs;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigsFetcher implements IConfigFetcher {
    private IConfigs configs;

    public ConfigsFetcher(IConfigs configs) { this.configs = configs; }

    public HashMap<String, String> fetch(String[] neededConfigs) {
        return new HashMap<String, String>();
    }
}
