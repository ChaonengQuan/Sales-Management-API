package com.chaonengquan.service;

import com.chaonengquan.init.AppInitializer;
import com.chaonengquan.model.Role;
import com.chaonengquan.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class UserServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private User user;
    private Role role1;
    private Role role2;


    @Before
    public void setup(){
        user = new User();
        user.setEmail("user12@gmail.com");
        user.setFirstName("LIYING");
        user.setLastName("Huang");
        user.setName("random name");
        user.setPassword("1111");
        user.setSecretKey("1222");

        role1 = new Role();
        role1.setName("role1_manager");
        role1.setAllowedCreate(true);
        role1.setAllowedDelete(true);
        role1.setAllowedRead(true);
        role1.setAllowedResource("yes");
        role1.setAllowedUpdate(true);

        role1 = roleService.save(role1);    //role1 now contains id

        role2 = new Role();
        role2.setName("role2_employee");
        role2.setAllowedCreate(false);
        role2.setAllowedDelete(false);
        role2.setAllowedRead(true);
        role2.setAllowedResource("no");
        role2.setAllowedUpdate(false);

        role2 = roleService.save(role2);    //role2 now contains id

        user.addRole(role1);
        user.addRole(role2);

        user = userService.save(user);
    }


    @Test
    public void getAllUserTest(){
        List<User> userList = userService.getAllUser();
        assertEquals(4, userList.size());
    }

    @Test
    public void getUserByCredential(){
        try {
            User targetUser = userService.getUserByCredential("dwang@training.ascendingdc.com", "25f9e794323b453885f5181f1b624d0b");
            assertEquals("dwang", targetUser.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanup(){
        roleService.delete(role1);
        roleService.delete(role2);
        userService.delete(user);
    }

}
