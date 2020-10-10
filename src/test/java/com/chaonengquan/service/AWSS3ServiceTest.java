package com.chaonengquan.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.Bucket;
import com.chaonengquan.init.AppInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AWSS3Service s3Service;

    String testBucketName = "chaonengquan-bucket-1";


    @Test
    public void uploadMultipartFileTest() throws IOException {
        String filename = "/Users/chaonengquan/Documents/sdeTraining/bucket-1/File2.txt";
        File file = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("multipartFile", file.getName(), "text/plain", IOUtils.toByteArray(fileInputStream));

        String objectKey = s3Service.uploadMultipartFile(testBucketName, multipartFile);

        boolean isExist = s3Service.isObjectExist(testBucketName, objectKey);
        assertTrue(isExist);
    }


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
