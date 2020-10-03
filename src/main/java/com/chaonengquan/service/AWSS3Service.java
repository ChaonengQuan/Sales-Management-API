package com.chaonengquan.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;
import java.util.List;

@Service
public class AWSS3Service {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;
    @Value("${aws.region}")
    private String awsRegion;
    @Value("{aws.secretKeyId}")
    private String keyId;
    @Value("{aws.secretKey}")
    private String key;


    public AWSS3Service() {
        this.amazonS3 = getS3ClientUsingDefaultChain();
    }

    private AmazonS3 getS3ClientUsingDefaultChain(){
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_1)
                .build();    //same as account region
        return s3Client;
    }



    /*Bucket Operations*/
    public boolean isBucketExist(String bucketName){
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        return isExist;
    }

    public List<Bucket> getBucketList() {
        return amazonS3.listBuckets();
    }

    public Bucket getBucket(String bucketName){
        Bucket targetBucket = null;
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucketName)) {
                targetBucket = b;
            }
        }
        return targetBucket;
    }

    public Bucket createBucket(String bucketName) {
        Bucket bucket = null;

        if(amazonS3.doesBucketExistV2(bucketName)){
            logger.info("Bucket {} already exists.\n", bucketName);
            bucket = getBucket(bucketName);
        }else{  //if bucket does not exist
            try{
                bucket = amazonS3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                logger.error("AmazonS3Exception when calling createBucket(), error = {}", e.getMessage());
            }
        }
        return bucket;
    }

    /**
     * Delete entire Bucket only when it's empty
     * @param bucketName - the name of the bucket
     * @return isDeleted
     */
    public boolean deleteBucket(String bucketName) {
        boolean status = false;
        Bucket targetBucket = getBucket(bucketName);

        ObjectListing object_listing = amazonS3.listObjects(bucketName);
        Iterator<?> iterator = object_listing.getObjectSummaries().iterator();

        if(iterator.hasNext()){     //bucket is not empty, don't delete
            logger.debug("Unable to delete, bucket is not empty!");
        }else{
            amazonS3.deleteBucket(bucketName);
            status = true;
        }

        return status;
    }

    /*Object in Bucket Operations*/

    public boolean isObjectExist(String bucketName, String objectKey){
        boolean isExist = amazonS3.doesObjectExist(bucketName, objectKey);
        return isExist;
    }

    public ObjectListing listObjects(String bucketName) {
        ObjectListing result = amazonS3.listObjects(bucketName);
        return result;
    }

    public void uploadObject(String bucketName, String key, String fullFilepath) {
        try {
            amazonS3.putObject(bucketName, key, new File(fullFilepath));
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException when calling uploadObject(), error = {}", e.getMessage());
        }
        logger.info("Upload complete!");
    }

    public void deleteObject(String bucketName, String objectKey){
        try {
            amazonS3.deleteObject(bucketName, objectKey);
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException when calling deleteObject(), error = {}", e.getMessage());
        }

    }

}
