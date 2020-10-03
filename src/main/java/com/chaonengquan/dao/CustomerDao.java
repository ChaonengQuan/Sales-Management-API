package com.chaonengquan.dao;

import com.chaonengquan.model.Customer;

import java.util.List;

public interface CustomerDao {

    /**
     * Save new Customer to the database
     * @param customer - new Customer
     * @return saved Customer
     */
    Customer save(Customer customer);

    /**
     * Delete existing Customer from the database
     * @param customer - existing Customer
     * @return true if success, false if fail
     */
    boolean delete(Customer customer);

    /**
     * Update existing Customer in the database
     * @param customer - existing Customer
     * @return updated Customer
     */
    Customer update(Customer customer);

    /**
     * Retrieve all Customer from the database
     * @return A list of Customer in the database
     */
    List<Customer> getAllCustomer();


    Customer getById(long id);
}
