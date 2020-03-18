package com.hammadltd.mainservice.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface ISecrets {
    String getQueueName();
    String getBucketName();
    String getEncryptionKey();
    String getIv();
}
