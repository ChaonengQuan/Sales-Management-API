package com.chaonengquan.service;


import com.amazonaws.services.s3.model.Bucket;
import com.chaonengquan.init.AppInitializer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AWSS3Service s3Service;

    @Test
    public void isBucketExistTest(){
        String bucketName = "chaonengquan-bucket-1";
        boolean isExist = s3Service.isBucketExist(bucketName);
        assertTrue(isExist);
    }

    @Test
    public void getBucketListTest(){
        List<Bucket> bucketList = s3Service.getBucketList();
        Assert.assertEquals(2, bucketList.size());
    }

    @Test
    public void isObjectExistTest(){
        String bucketName = "chaonengquan-bucket-1";
        String objectKey = "File1.txt";     //hard coded
        boolean isExist = s3Service.isObjectExist(bucketName, objectKey);
        Assert.assertTrue(isExist);
    }


}
