package com.chaonengquan.service;

import com.chaonengquan.dao.RoleDao;
import com.chaonengquan.dao.UserDao;
import com.chaonengquan.model.Role;
import com.chaonengquan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role save(Role role){
        return roleDao.save(role);
    }

    public boolean delete(Role role){
        return roleDao.delete(role);
    }

}
