package com.hammadltd.mainservice;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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

        String filePath = writeToFile(info);

        if (filePath == null) return "Fail";

        String[] pathArr = filePath.split("[/\\\\]");
        String fileName = pathArr[pathArr.length - 1];

        boolean fileUploadStatus = uploadInfoToS3(filePath, fileName);
        boolean messagePushStatus = pushHashToQueue(info.getId(), hash);

        deleteFilePath(filePath);

        return fileUploadStatus && messagePushStatus ? "Success" : "Fail";
    }

    private String writeToFile(Info info) {
        try {
            Path tempDirPath = Files.createTempDirectory("tempdir");
            String infoFileName = "temp-" + UUID.randomUUID().toString() + ".txt";
            Path infoFilePath = tempDirPath.resolve(infoFileName);

            File infoFile = new File(infoFilePath.toString());
            infoFile.createNewFile();

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(info);

            FileWriter myWriter = new FileWriter(infoFilePath.toString());
            myWriter.write(json);
            myWriter.close();

            return infoFilePath.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean uploadInfoToS3(String path, String fileName) {
        String bucket_name = secrets.getBucketName();
        System.out.format("Uploading %s to S3 bucket %s...\n", path, bucket_name);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_2).build();
        try {
            s3.putObject(bucket_name, fileName, new File(path));
            System.out.println("Uploaded Successfully");
            return true;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return false;
    }

    private boolean pushHashToQueue(String id, String hash) {
        System.out.println("Pushing message to AWS SQS");
        try {
            String queueName = secrets.getQueueName();
            final AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                    .withRegion(Regions.US_EAST_2).build();
            String queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();

            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(id + " " + hash)
                    .withDelaySeconds(5);
            sqs.sendMessage(send_msg_request);
            System.out.println("Message Pushed Successfully");
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void deleteFilePath(String path) {
        Path parentDir = Paths.get(path).getParent();
        System.out.println("Deleting info directory: " + parentDir);
        try {
            Files.delete(Paths.get(path));
            Files.delete(parentDir);
            System.out.println("Deleted Successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
