//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.CustomerDao;
//import com.chaonengquan.model.Customer;
//import org.junit.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.junit.Assert.assertNotNull;
//
//
//public class CustomerDaoTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private CustomerDao customerDao;
//    private Customer dummyCustomer;
//
//    @Before
//    public void setup(){
//        customerDao = new CustomerDaoJdbcImpl();
//
//        dummyCustomer = new Customer();
//        dummyCustomer.setFirstName("dummy_first_name_JDBC");
//        dummyCustomer.setLastName("dummy_last_name_JDBC");
//        dummyCustomer.setEmail("dummy@JDBC.com");
//        dummyCustomer.setAddress("JDBC");
//    }
//
//    @Test
//    public void saveTest(){
//        Customer savedCustomer = customerDao.save(dummyCustomer);
//        logger.info("===Saved new customer={}", savedCustomer);
//        assertNotNull(savedCustomer);
//    }
//
//    /*Add more test when need to, but since we are using Hibernate no such need now*/
//
////    @After
////    public void cleanup(){
////        customerDao.delete(savedCustomer);  //delete dummy Customer from database, savedCustomer contains id value
////    }
//
//}
