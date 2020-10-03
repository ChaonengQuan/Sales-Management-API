package com.chaonengquan.service;

import com.chaonengquan.dao.CustomerOrderDao;
import com.chaonengquan.model.CustomerOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerOrderService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerOrderDao customerOrderDao;

    public CustomerOrder save(CustomerOrder customerOrder){
        return customerOrderDao.save(customerOrder);
    }

    public boolean delete(CustomerOrder customerOrder){
        return customerOrderDao.delete(customerOrder);
    }

    public CustomerOrder update(CustomerOrder customerOrder){
        return customerOrderDao.update(customerOrder);
    }

    public List<CustomerOrder> getAllCustomerOrder(){
        return customerOrderDao.getAllCustomerOrder();
    }

}
