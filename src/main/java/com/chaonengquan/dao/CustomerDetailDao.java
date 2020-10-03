package com.chaonengquan.dao;

import com.chaonengquan.model.CustomerDetail;

import java.util.List;

public interface CustomerDetailDao {

    /**
     * Save new CustomerDetails to the database
     * @param customerDetail - new CustomerDetail
     * @return
     */
    CustomerDetail save(CustomerDetail customerDetail);

    /**
     * Delete existing CustomerDetails in the database
     * @param customerDetail - existing CustomerDetail
     * @return
     */
    boolean delete(CustomerDetail customerDetail);

    /**
     * Update existing CustomerDetails in the database
     * @param customerDetail - existing CustomerDetail
     * @return
     */
    CustomerDetail update(CustomerDetail customerDetail);

    /**
     * Retrieve all existing CustomerDetails from the database
     * @return A list of CustomerDetails in the database
     */
    List<CustomerDetail> getAllCustomer();

}
