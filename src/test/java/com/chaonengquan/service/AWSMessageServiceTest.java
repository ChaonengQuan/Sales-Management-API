package com.chaonengquan.service;

import com.chaonengquan.init.AppInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSMessageServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AWSMessageService awsMessageService;


    @Before
    public void setup(){ }

    @Test
    public void getQueueUrlTest(){
        String queueName = "testQueue";
        String queueUrl = awsMessageService.getQueueUrl(queueName);
        logger.info("================ queueUrl={}", queueUrl);
        Assert.assertNotNull(queueUrl);
    }



}
