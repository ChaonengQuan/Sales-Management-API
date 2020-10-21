package com.chaonengquan.service;

import com.amazonaws.services.s3.AmazonS3;
import com.chaonengquan.init.AppInitializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)    //also includes Mockito?
@SpringBootTest(classes = AppInitializer.class)
public class AWSS3ServiceMockTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    private AmazonS3 amazonS3Mock = mock(AmazonS3.class);
    @Mock
    private AmazonS3 amazonS3Mock;
    @InjectMocks
    private AWSS3Service amazonS3Service;

    @Before
    public void setup(){ }

    @Test
    public void BucketDoExist(){
        when(amazonS3Mock.doesBucketExistV2(anyString())).thenReturn(Boolean.TRUE);

        boolean isExist = amazonS3Service.isBucketExist(anyString());
        Assert.assertTrue(isExist);
    }

    //add more Mock test




}
