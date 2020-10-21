package com.chaonengquan.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
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

    /**
     * This causes a listener container to be created on the specified destination using a ContainerFactory.
     * If not set, a default container factory is assumed to be available with
     * a bean name of jmsListenerContainerFactory unless an explicit default has been provided through configuration.
     */
    //@JmsListener(destination = "${VM-option}")
    @JmsListener(destination = "testQueue") //find the bean in the JmsConfig //also add this in the VM option
    public void receiveMessage(String message){
        logger.info("========================@@@###jmsListener received message = {}", message);
    }



}
