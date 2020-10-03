package com.chaonengquan.dao;

import com.chaonengquan.model.Role;

import java.util.List;

public interface RoleDao {

    Role save(Role role);
    boolean delete(Role role);
//    Role update(Role role);
//    List<Role> getAllRole();
}
