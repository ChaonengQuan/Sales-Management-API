package com.chaonengquan.repository;

import com.chaonengquan.dao.CustomerDao;
import com.chaonengquan.init.AppInitializer;
import com.chaonengquan.model.Customer;
import com.chaonengquan.model.CustomerDetail;
import com.chaonengquan.model.CustomerOrder;
import com.chaonengquan.model.Item;
import com.chaonengquan.repositoy.CustomerDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
public class CustomerDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerDao customerDao;

    private Customer dummyCustomer;
    private CustomerOrder customerOrder1;
    private CustomerOrder customerOrder2;
    private Item item1;
    private Item item2;
    private CustomerDetail dummyCustomerDetail;

    //Don't need this because of Autowired
//    @BeforeClass
//    public static void setupOnce(){
//        customerDao = new CustomerDaoImpl();
//    }

    @Before
    public void setup(){
        //initialize testCustomer's fields
        dummyCustomer = new Customer();
        dummyCustomer.setFirstName("first_name_Hibernate");
        dummyCustomer.setLastName("last_name_Hibernate");
        dummyCustomer.setEmail("test@Hibernate.com");
        dummyCustomer.setAddress("Hibernate Ave");
        //initialize order1's fields
        customerOrder1 = new CustomerOrder();
        customerOrder1.setAddress("Irvine, California");
        customerOrder1.setPayment("Mastercard");
        customerOrder1.setAmount(299.99);
        //initialize order2's fields
        customerOrder2 = new CustomerOrder();
        customerOrder2.setAddress("NYC, NY");
        customerOrder2.setPayment("Visa");
        customerOrder2.setAmount(999.99);
        //initialize product1's fields
        item1 = new Item();
        item1.setItemName("Luxury toilet paper");
        item1.setPrice(999);
        //initialize product2's fields
        item2 = new Item();
        item2.setItemName("PS5");
        item2.setPrice(299.99);
        //connect product1 with order1
        customerOrder1.addItem(item1);
        //connect order1 with testCustomer
        dummyCustomer.addCustomerOrder(customerOrder1);
        //connect product2 with order2
        customerOrder2.addItem(item2);
        //connect order2 with testCustomer
        dummyCustomer.addCustomerOrder(customerOrder2);
        //connect customer and customerDetails
        dummyCustomerDetail = new CustomerDetail();
        dummyCustomerDetail.setDescription("This is a test customer details");
        dummyCustomerDetail.setGender("Female");
        dummyCustomerDetail.setMembership("member");
        //connect Customer and CustomerDetail
        dummyCustomer.setCustomerDetail(dummyCustomerDetail);
        dummyCustomerDetail.setCustomer(dummyCustomer);
        customerDao.save(dummyCustomer);
    }

    @Test
    public void saveTest(){
        Customer savedCustomer = customerDao.save(dummyCustomer);
        assertEquals(dummyCustomer.getEmail(), savedCustomer.getEmail());
        logger.info("saved customer = {}", savedCustomer);
    }


    @Test
    public void updateTest(){
        //TODO finish this
    }


    @Test
    public void getAllCustomers(){
        List<Customer> allCustomers = customerDao.getAllCustomer();
        assertEquals("Number of total records should be 6",6, allCustomers.size());
        logger.info("all customers = {}", allCustomers);
    }




    @After
    public void cleanup(){
        customerDao.delete(dummyCustomer);                  //delete after Junit Test
        customerDao = null;
    }







}
