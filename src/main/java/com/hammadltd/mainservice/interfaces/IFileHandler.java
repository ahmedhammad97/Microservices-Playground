package com.hammadltd.mainservice.interfaces;

import com.hammadltd.mainservice.models.Info;
import org.springframework.stereotype.Component;

@Component
public interface IFileHandler {
    String writeToFile(Info info);
    void deleteFilePath(String path);
}
