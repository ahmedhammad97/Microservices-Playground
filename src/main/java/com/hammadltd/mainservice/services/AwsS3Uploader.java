package com.hammadltd.mainservice.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.hammadltd.mainservice.interfaces.IAwsS3Uploader;
import com.hammadltd.mainservice.interfaces.ISecrets;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AwsS3Uploader implements IAwsS3Uploader {
    private ISecrets secrets;

    public AwsS3Uploader(ISecrets secrets) { this.secrets = secrets; }

    public boolean uploadInfoToS3(String path, String fileName) {
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
}
