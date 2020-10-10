package com.chaonengquan.controller;


import com.chaonengquan.service.AWSS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping(value = {"/file"})
public class AWSS3Controller {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AWSS3Service awss3Service;

    private String testBucketName = "chaonengquan-bucket-1";

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //you can return either s3 key or file url
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("test file name:"+file.getOriginalFilename());
        try {
            return awss3Service.uploadMultipartFile(testBucketName, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






    @GetMapping(params={"fileName"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFileUrlForDownloading(@RequestParam("fileName") String fileName) {
//        request.getSession()
//        Resource resource = null;
        String msg = "The file doesn't exist.";
        ResponseEntity responseEntity;
        try {
            String url = awss3Service.generatePresignedURLForDownloading(fileName);
            logger.debug(msg);
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(url);
        }
        catch (Exception ex) {
            responseEntity = ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(ex.getMessage());
            logger.debug(ex.getMessage());
        }
        return responseEntity;
    }



}
