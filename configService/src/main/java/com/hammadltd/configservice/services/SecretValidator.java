package com.hammadltd.configservice.services;

import com.hammadltd.configservice.interfaces.ISecretValidator;
import com.hammadltd.configservice.interfaces.ISecrets;
import com.hammadltd.configservice.models.Secrets;
import org.springframework.stereotype.Service;

@Service
public class SecretValidator implements ISecretValidator {
    private ISecrets secrets = new Secrets();

    public boolean validate(String secret) {
        return secrets.getConfigsSecretKey().equals(secret);
    }
}
