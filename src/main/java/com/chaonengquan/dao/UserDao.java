package com.chaonengquan.dao;

import com.chaonengquan.model.User;

import java.util.List;

public interface UserDao {

    User save(User user);
    boolean delete(User user);
//    User update(User user);
    List<User> getAllUser();

    User getUserByCredential(String email, String password) throws Exception;

    User getUserByName(String name);
}
