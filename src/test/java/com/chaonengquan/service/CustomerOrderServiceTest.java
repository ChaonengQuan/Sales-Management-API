package com.chaonengquan.service;


import com.chaonengquan.init.AppInitializer;
import com.chaonengquan.model.CustomerOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class CustomerOrderServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerOrderService customerOrderService;



    @Before
    public void setup(){

    }


    @Test
    public void getAllCustomerOrderTest(){
        List<CustomerOrder> result = customerOrderService.getAllCustomerOrder();
        assertEquals("number of total records is 5",5, result.size());
    }


    @After
    public void cleanup(){

    }
}
