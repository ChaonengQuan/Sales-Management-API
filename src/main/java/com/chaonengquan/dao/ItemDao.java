package com.chaonengquan.dao;

import com.chaonengquan.model.Item;

import java.util.List;

public interface ItemDao {

    /**
     * Save new Item to the database
     * @param item - new Item
     * @return saved Item
     */
    Item save(Item item);

    /**
     * Delete existing Item from the database
     * @param item - existing database
     * @return true if success, false if fail
     */
    boolean delete(Item item);

    /**
     * Update existing Item in the database
     * @param item - existing Item
     * @return Updated Item
     */
    Item update(Item item);

    /**
     * Retrieve all Item in the database
     * @return A List of Item in the databse
     */
    List<Item> getAllItem();

}
