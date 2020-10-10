package com.chaonengquan.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AWSMessageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AmazonSQS amazonSQS;



    public String createQueue(String queueName){
        String queueUrl = null;

        try{
            queueUrl = getQueueUrl(queueName);      //check if it exist already
        }catch (QueueDoesNotExistException e){                                                     //run time exceptions
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(queueName);
            queueUrl = amazonSQS.createQueue(createQueueRequest).getQueueUrl();                    //!!!! Fluent API, it's like a chain !!!!
            //a method return it self
            //Also look up builder design pattern + fluent
        }

        logger.info(queueUrl);

        return queueUrl;
    }







    public String getQueueUrl(String queueName){
        GetQueueUrlResult getQueueUrlResult = amazonSQS.getQueueUrl(queueName);
        return getQueueUrlResult.getQueueUrl();

    }





}
