//package com.chaonengquan.jdbc;
//
//import com.chaonengquan.dao.ItemDao;
//import com.chaonengquan.model.Item;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import static org.junit.Assert.assertNotNull;
//
//public class ItemDaoJdbcImplTest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private ItemDao itemDao;
//    private Item dummyItem;
//
//    @Before
//    public void setup(){
//        itemDao = new ItemDaoJdbcImpl();
//        dummyItem = new Item();
//
//        dummyItem.setItemName("JDBC item");
//        dummyItem.setPrice(200.00);
//        dummyItem.setOrderId(1);    //assume it is associated with the id=1 customer
//    }
//
//    @Test
//    public void saveTest(){
//        Item savedItem = itemDao.save(dummyItem);
//        logger.info("===Saved new Item={}", savedItem);
//        assertNotNull(savedItem);
//    }
//
//    /*Add more test when need to, but since we are using Hibernate no such need now*/
//
////    @After
////    public void cleanup(){
////        itemDao.delete(dummyItem);
////    }
//}
