package com.hammadltd.configservice.interfaces;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public interface IConfigFetcher {
    HashMap<String, String> fetch(String[] neededConfigs);
}
