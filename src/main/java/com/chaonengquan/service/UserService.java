package com.chaonengquan.service;

import com.chaonengquan.dao.UserDao;
import com.chaonengquan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User save(User user){
        return userDao.save(user);
    }

    public boolean delete(User user){
        return userDao.delete(user);
    }

    public List<User> getAllUser(){return userDao.getAllUser(); }

    public User getUserByCredential(String email, String password) throws Exception{
        return userDao.getUserByCredential(email,password);
    }

}
