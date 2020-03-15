package com.hammadltd.configservice.services;

import com.hammadltd.configservice.interfaces.ISecretValidator;
import com.hammadltd.configservice.interfaces.ISecrets;
import org.springframework.stereotype.Service;

@Service
public class SecretValidator implements ISecretValidator {
    private ISecrets secrets;

    public SecretValidator(ISecrets secrets) { this.secrets = secrets; }

    public boolean validate(String secret) {
        return secrets.getConfigsSecretKey().equals(secret);
    }
}
