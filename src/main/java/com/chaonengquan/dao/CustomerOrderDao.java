package com.chaonengquan.dao;

import com.chaonengquan.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderDao {

    /**
     * Save new CustomerOrder to the database
     * @param customerOrder - new CustomerOrder
     * @return saved CustomerOrder
     */
    CustomerOrder save(CustomerOrder customerOrder);

    /**
     * Delete existing CustomerOrder from the database
     * @param customerOrder - existing CustomerOrder
     * @return true if success, false if fail
     */
    boolean delete(CustomerOrder customerOrder);

    /**
     * Update existing CustomerOrder in the database
     * @param customerOrder - existing CustomerOrder
     * @return Updated CustomerOrder
     */
    CustomerOrder update(CustomerOrder customerOrder);

    /**
     * Retrieve all CustomerOrder from the database
     * @return A list of CustomerOrder in the database
     */
    List<CustomerOrder> getAllCustomerOrder();
}
