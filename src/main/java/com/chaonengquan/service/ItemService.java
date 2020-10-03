package com.chaonengquan.service;

import com.chaonengquan.dao.ItemDao;
import com.chaonengquan.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemDao itemDao;

    public Item save(Item item){
        return itemDao.save(item);
    }

    public boolean delete(Item item){
        return itemDao.delete(item);
    }

    public Item update(Item item){
        return itemDao.update(item);
    }

    public List<Item> getAllItem(){
        return  itemDao.getAllItem();
    }

}
