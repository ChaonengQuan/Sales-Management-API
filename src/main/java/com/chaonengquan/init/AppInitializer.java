package com.chaonengquan.init;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.chaonengquan"})     //setup components
@ServletComponentScan("com.chaonengquan.filter")                    //setup filters
public class AppInitializer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(AppInitializer.class, args);
    }

}
