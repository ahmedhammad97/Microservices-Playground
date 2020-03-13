package com.hammadltd.mainservice.controllers;

import com.hammadltd.mainservice.interfaces.IAwsS3Uploader;
import com.hammadltd.mainservice.interfaces.IAwsSqsPusher;
import com.hammadltd.mainservice.interfaces.IFileHandler;
import com.hammadltd.mainservice.interfaces.ISecrets;
import com.hammadltd.mainservice.models.Info;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private ISecrets secrets;
    private IAwsS3Uploader awsS3Uploader;
    private IAwsSqsPusher awsSqsPusher;
    private IFileHandler fileHandler;

    public InfoController(
            ISecrets secrets,
            IAwsS3Uploader awsS3Uploader,
            IAwsSqsPusher awsSqsPusher,
            IFileHandler fileHandler
    ) {
        this.secrets = secrets;
        this.awsS3Uploader = awsS3Uploader;
        this.awsSqsPusher = awsSqsPusher;
        this.fileHandler = fileHandler;
    }

    @RequestMapping(value ="/info", method = RequestMethod.POST)
    public String infoProcessor(@RequestBody Info info) {
        info.setUniqueId();
        String hash = info.getSecretData(this.secrets.getEncryptionKey());

        String filePath = fileHandler.writeToFile(info);

        if (filePath == null) return "Fail";

        String[] pathArr = filePath.split("[/\\\\]");
        String fileName = pathArr[pathArr.length - 1];

        boolean fileUploadStatus = awsS3Uploader.uploadInfoToS3(filePath, fileName);
        boolean messagePushStatus = awsSqsPusher.pushHashToQueue(info.getId(), hash);

        fileHandler.deleteFilePath(filePath);

        return fileUploadStatus && messagePushStatus ? "Success" : "Fail";
    }
}
