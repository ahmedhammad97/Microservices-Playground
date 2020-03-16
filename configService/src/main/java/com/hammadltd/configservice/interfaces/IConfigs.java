package com.hammadltd.configservice.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface IConfigs {
    String getConfig(String configName);
    boolean hasConfig(String config);
}
