package com.hammadltd.configservice.services;

import com.hammadltd.configservice.interfaces.IConfigFetcher;
import com.hammadltd.configservice.interfaces.IConfigs;
import com.hammadltd.configservice.models.Configs;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConfigsFetcher implements IConfigFetcher {
    private IConfigs configs = new Configs();

    public HashMap<String, String> fetch(String[] neededConfigs) {
        HashMap<String, String> fetchedConfigs = new HashMap<>();
        for (String config : neededConfigs) {
            if (configs.hasConfig(config)) {
                fetchedConfigs.put(config, configs.getConfig(config));
            }
            else {
                fetchedConfigs.put(config, null);
            }
        }
        return fetchedConfigs;
    }
}
