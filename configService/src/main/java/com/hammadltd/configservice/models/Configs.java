package com.hammadltd.configservice.models;

import com.hammadltd.configservice.interfaces.IConfigs;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "configs")
@PropertySource("classpath:configs.properties")
public class Configs implements IConfigs {
    private String config1;
    private String config2;
    private String config3;
    private String config4;

    private HashMap<String, String> configsMap;

    public String getConfig1() {
        return config1;
    }

    public void setConfig1(String config1) {
        this.config1 = config1;
    }

    public String getConfig2() {
        return config2;
    }

    public void setConfig2(String config2) {
        this.config2 = config2;
    }

    public String getConfig3() {
        return config3;
    }

    public void setConfig3(String config3) {
        this.config3 = config3;
    }

    public String getConfig4() {
        return config4;
    }

    public void setConfig4(String config4) {
        this.config4 = config4;
    }

    @Override
    public boolean hasConfig(String config) {
        return this.configsMap.containsKey(config);
    }

    private void updateConfigsMap() {
        this.configsMap = new HashMap<String, String>() {{
            put("config1", getConfig1());
            put("config2", getConfig2());
            put("config3", getConfig3());
            put("config4", getConfig4());
        }};
    }

    public String getConfig(String configName) {
        this.updateConfigsMap();
        return this.configsMap.get(configName);
    }
}
