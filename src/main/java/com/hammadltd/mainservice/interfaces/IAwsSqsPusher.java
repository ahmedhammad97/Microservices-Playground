package com.hammadltd.mainservice.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface IAwsSqsPusher {
    boolean pushHashToQueue(String id, String hash);
}
