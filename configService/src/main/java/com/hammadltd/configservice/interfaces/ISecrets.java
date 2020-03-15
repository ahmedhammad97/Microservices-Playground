package com.hammadltd.configservice.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface ISecrets {
    String getConfigsSecretKey();
}
