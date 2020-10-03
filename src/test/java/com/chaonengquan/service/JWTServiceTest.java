package com.chaonengquan.service;


import com.chaonengquan.dao.UserDao;
import com.chaonengquan.init.AppInitializer;
import com.chaonengquan.model.User;
import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class JWTServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void setup(){
        String username = "dwang";
        user = userDao.getUserByName(username);
    }


    /**
     * 1. get token by call jwtService
     *   2. assert not null
     *   3. parse the token string using "." to get a string array
     *   4. assert the string array size == 3
     *   5. logger info token value
     */
    @Test
    public void generateTokenTest(){
        String token = jwtService.generateToken(user);
        assertNotNull(token);

        String[] tokenArray = token.split("\\.");   //jwt token has three parts, separated by '.'
        assertEquals(3, tokenArray.length);
        logger.info("@@@###$$$ generated token is {}", token);
    }

    /**
     *    1. generate a token using the user --- jwtService.
     *     2. jwt service decry token to get claims
     *     3. get subject of the token from Claims
     *     4. assert user.getName == subject
     */
    @Test
    public void decryptJwtTokenTest() {
        String token = jwtService.generateToken(user);

        Claims decryptedToken = jwtService.decryptJwtToken(token);
        String subject = decryptedToken.getSubject();
        assertEquals("dwang", subject);      //name was encrypted as the subject
    }

    /**
     *   1. get a toke using the user
     *   2. assert not null
     *   3. call jwtService.hasTokenExpired(token)
     *   4. assertFalse(flag)
     */
    @Test
    public void tokenHasNotExpiredTest() {
        String token = jwtService.generateToken(user);
        assertNotNull(token);

        boolean isExpired = jwtService.hasTokenExpired(token);
        assertFalse(isExpired);
    }


}
