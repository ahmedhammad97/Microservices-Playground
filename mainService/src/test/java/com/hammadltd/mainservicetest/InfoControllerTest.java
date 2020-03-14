package com.hammadltd.mainservicetest;

import com.hammadltd.mainservice.App;
import com.hammadltd.mainservice.controllers.InfoController;
import com.hammadltd.mainservice.interfaces.IAwsS3Uploader;
import com.hammadltd.mainservice.interfaces.IAwsSqsPusher;
import com.hammadltd.mainservice.interfaces.IFileHandler;
import com.hammadltd.mainservice.interfaces.ISecrets;
import com.hammadltd.mainservice.models.Info;
import com.hammadltd.mainservice.models.Secrets;
import com.hammadltd.mainservice.services.AwsS3Uploader;
import com.hammadltd.mainservice.services.AwsSqsPusher;
import com.hammadltd.mainservice.services.FileHandler;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= App.class)
@WebMvcTest(InfoController.class)
class InfoControllerTest {
    private ISecrets secrets = new Secrets();
    private IAwsS3Uploader awsS3Uploader = new AwsS3Uploader(secrets);
    private IAwsSqsPusher awsSqsPusher = new AwsSqsPusher(secrets);
    private IFileHandler fileHandler = new FileHandler();
    private InfoController infoController = new InfoController(secrets, awsS3Uploader, awsSqsPusher, fileHandler);

    @Test
    void infoProcessor() {
        Info mockedInfo = new Info("Hammad", 25, Mockito.anyString());

        Mockito.when(secrets.getBucketName()).thenReturn("bla");
        Mockito.when(secrets.getEncryptionKey()).thenReturn("bla");
        Mockito.when(secrets.getQueueName()).thenReturn("bla");

        Mockito.when(awsS3Uploader.uploadInfoToS3(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(awsSqsPusher.pushHashToQueue(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        Mockito.when(fileHandler.writeToFile(mockedInfo)).thenReturn(Mockito.anyString());

        String result = infoController.infoProcessor(mockedInfo);

        assertNotNull(result);
        assertNotEquals(result, "Fail");
        assertEquals(result, "Success");


    }
}