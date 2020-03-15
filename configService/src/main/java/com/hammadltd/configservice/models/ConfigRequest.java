package com.hammadltd.configservice.models;

public class ConfigRequest {
    private String[] neededConfigs;
    private String secretKey;

    public String[] getNeededConfigs() {
        return neededConfigs;
    }

    public void setNeededConfigs(String[] neededConfigs) {
        this.neededConfigs = neededConfigs;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
