//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.CustomerOrderDao;
//import com.chaonengquan.model.CustomerOrder;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.junit.Assert.assertNotNull;
//
//public class CustomerOrderDaoTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private CustomerOrderDao customerOrderDao;
//    private CustomerOrder dummyCustomerOrder;
//
//    @Before
//    public void setup(){
//        customerOrderDao = new CustomerOrderDaoJdbcImpl();
//        dummyCustomerOrder = new CustomerOrder();
//
//        dummyCustomerOrder.setAddress("JDBC street");
//        dummyCustomerOrder.setPayment("JDBC card");
//        dummyCustomerOrder.setAmount(100.00);
//        dummyCustomerOrder.setCustomerID(1);    //assume it is associated with the id=1 customer
//    }
//
//    @Test
//    public void saveTest(){
//        CustomerOrder savedCustomerOrder =  customerOrderDao.save(dummyCustomerOrder);
//        logger.info("===Saved new CustomerOrder={}", savedCustomerOrder);
//        assertNotNull(savedCustomerOrder);
//    }
//
//    /*Add more test when need to, but since we are using Hibernate no such need now*/
//
////    @After
////    public void cleanup(){
////        customerOrderDao.delete(dummyCustomerOrder);
////    }
//
//}
