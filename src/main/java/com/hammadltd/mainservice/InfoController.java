package com.hammadltd.mainservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private Secrets secrets;

    public InfoController(Secrets secrets) {
        this.secrets = secrets;
    }

    @RequestMapping(value ="/info", method = RequestMethod.POST)
    public String sayHi(@RequestBody Info info) {
        info.setUniqueId();
        String hash = info.getSecretData(this.secrets.getEncryptionKey());

        boolean fileUploadStatus = uploadInfoToS3(info);
        boolean messagePushStatus = pushHashToQueue(info.getId(), hash);

        return fileUploadStatus && messagePushStatus ? "Success" : "Fail";
    }

    private boolean uploadInfoToS3(Info info) {
        return true;
    }

    private boolean pushHashToQueue(String id, String hash) {
        return true;
    }
}
