package com.chaonengquan.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class AWSS3Service {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucketName;

//    @Value("{aws.secretKeyId}")       //Java VM option
//    private String keyId;
//    @Value("{aws.secretKey}")
//    private String key;


    public String uploadMultipartFile(String bucketName, MultipartFile multipartFile) throws IOException {
        //add metadata before uploading
        logger.info("####----- line No.1 before calling amazonS3.putObject");
        String uuid = UUID.randomUUID().toString();
        logger.info("####----- line No.2 before calling amazonS3.putObject");
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("####----- line No.3 before calling amazonS3.putObject");

        int dotIndex = originalFilename.indexOf(".");
        String newRandomFilename = originalFilename.substring(0, dotIndex) + "_" + uuid + originalFilename.substring(dotIndex);
        logger.info("####----- line No.4 before calling amazonS3.putObject, newRandomFilename is {}", newRandomFilename);
        logger.info("####----- line No.5 before calling amazonS3.putObject, bucketName is {}", bucketName);


        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());
        logger.info("####----- line No.6 before calling amazonS3.putObject");
        amazonS3.putObject(bucketName, newRandomFilename, multipartFile.getInputStream(), objectMetadata);

        return newRandomFilename;
    }


    public File downloadObject(String bucketName, String objectKey, String destinationFullPath) throws IOException {
        S3Object s3Object = amazonS3.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        File destFile = new File(destinationFullPath);
        FileUtils.copyInputStreamToFile(inputStream, destFile);
        return destFile;
    }


    /*Bucket Operations*/
    public boolean isBucketExist(String bucketName) {
        boolean isExist = amazonS3.doesBucketExistV2(bucketName);
        return isExist;
    }

    public List<Bucket> getBucketList() {
        return amazonS3.listBuckets();
    }

    public Bucket getBucket(String bucketName) {
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

        if (amazonS3.doesBucketExistV2(bucketName)) {
            logger.info("Bucket {} already exists.\n", bucketName);
            bucket = getBucket(bucketName);
        } else {  //if bucket does not exist
            try {
                bucket = amazonS3.createBucket(bucketName);
            } catch (AmazonS3Exception e) {
                logger.error("AmazonS3Exception when calling createBucket(), error = {}", e.getMessage());
            }
        }
        return bucket;
    }

    /**
     * Delete entire Bucket only when it's empty
     *
     * @param bucketName - the name of the bucket
     * @return isDeleted
     */
    public boolean deleteBucket(String bucketName) {
        boolean status = false;
        Bucket targetBucket = getBucket(bucketName);

        ObjectListing object_listing = amazonS3.listObjects(bucketName);
        Iterator<?> iterator = object_listing.getObjectSummaries().iterator();

        if (iterator.hasNext()) {     //bucket is not empty, don't delete
            logger.debug("Unable to delete, bucket is not empty!");
        } else {
            amazonS3.deleteBucket(bucketName);
            status = true;
        }

        return status;
    }

    /*Object in Bucket Operations*/

    public boolean isObjectExist(String bucketName, String objectKey) {
        boolean isExist = amazonS3.doesObjectExist(bucketName, objectKey);
        return isExist;
    }

    public ObjectListing listObjects(String bucketName) {
        ObjectListing result = amazonS3.listObjects(bucketName);
        return result;
    }

    /**
     * @param bucketName
     * @param key          - filename
     * @param fullFilepath
     */
    public void uploadObject(String bucketName, String key, String fullFilepath) {
        try {
            amazonS3.putObject(bucketName, key, new File(fullFilepath));
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException when calling uploadObject(), error = {}", e.getMessage());
        }
        logger.info("Upload complete!");
    }

    public void deleteObject(String bucketName, String objectKey) {
        try {
            amazonS3.deleteObject(bucketName, objectKey);
        } catch (AmazonServiceException e) {
            logger.error("AmazonServiceException when calling deleteObject(), error = {}", e.getMessage());
        }

    }


    public String generatePresignedURLForUploading(String bucketName, String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "PUT");
    }

    public String generatePresignedURLForDownloading(String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public String generatePresignedURLForDownloading(String bucketName, String objectKey) {
        return generatePresignedURL(bucketName, objectKey, "GET");
    }

    public String generatePresignedURL(String bucketName, String objectKey, String httpMethodString) {
        // Set the pre-signed URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;                                //1 hour
        expiration.setTime(expTimeMillis);
        // Generate the pre-signed URL.
        logger.info("Generating pre-signed URL.");
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.valueOf(httpMethodString))
                        .withExpiration(expiration);
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

}
