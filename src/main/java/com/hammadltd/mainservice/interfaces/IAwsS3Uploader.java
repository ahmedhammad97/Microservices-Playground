package com.hammadltd.mainservice.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface IAwsS3Uploader {
    boolean uploadInfoToS3(String path, String fileName);
}
