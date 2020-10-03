package com.chaonengquan.service;

import com.chaonengquan.dao.CustomerDao;
import com.chaonengquan.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerDao customerDao;

    public Customer save(Customer customer){
        return customerDao.save(customer);
    }

    public boolean delete(Customer customer){
        return customerDao.delete(customer);
    }

    public Customer update(Customer customer){
        return customerDao.update(customer);
    }

    public List<Customer> getAllCustomer(){
        return customerDao.getAllCustomer();
    }

    public Customer getById(long id){
        return customerDao.getById(id);
    }

}
