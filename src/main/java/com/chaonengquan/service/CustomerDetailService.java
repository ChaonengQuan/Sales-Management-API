package com.chaonengquan.service;

import com.chaonengquan.dao.CustomerDetailDao;
import com.chaonengquan.model.CustomerDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDetailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerDetailDao customerDetailDao;

    public CustomerDetail save(CustomerDetail customerDetail){
        return customerDetailDao.save(customerDetail);
    }

    public boolean delete(CustomerDetail customerDetail){
        return customerDetailDao.delete(customerDetail);
    }

    public CustomerDetail update(CustomerDetail customerDetail){
        return customerDetailDao.update(customerDetail);
    }

    public List<CustomerDetail> getAllCustomerDetail(){
        return customerDetailDao.getAllCustomer();
    }

}
