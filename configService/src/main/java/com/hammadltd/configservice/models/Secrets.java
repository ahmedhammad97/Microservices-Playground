package com.hammadltd.configservice.models;

import com.hammadltd.configservice.interfaces.ISecrets;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "secrets")
@PropertySource("classpath:secrets.properties")
public class Secrets implements ISecrets {
    private String configsSecretKey;

    public String getConfigsSecretKey() {
        return configsSecretKey;
    }

    public void setConfigsSecretKey(String configsSecretKey) {
        this.configsSecretKey = configsSecretKey;
    }

    public Secrets() { }
}