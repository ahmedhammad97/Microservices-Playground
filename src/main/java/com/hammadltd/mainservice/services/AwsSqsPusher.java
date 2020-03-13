package com.hammadltd.mainservice.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.hammadltd.mainservice.interfaces.IAwsSqsPusher;
import com.hammadltd.mainservice.interfaces.ISecrets;
import org.springframework.stereotype.Service;

@Service
public class AwsSqsPusher implements IAwsSqsPusher {
    private ISecrets secrets;

    public AwsSqsPusher(ISecrets secrets) { this.secrets = secrets; }

    public boolean pushHashToQueue(String id, String hash) {
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
}
