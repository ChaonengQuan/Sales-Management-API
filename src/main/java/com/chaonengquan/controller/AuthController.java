package com.chaonengquan.controller;

import com.chaonengquan.model.User;
import com.chaonengquan.service.JWTService;
import com.chaonengquan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value={"/auth"})
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    JWTService jwtService;

    private String errorMsg = "The email or password is not correct.";
    private String tokenKeyWord = "Authorization";
    private String tokenType = "Bearer";

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> authenticate(@RequestBody User user) throws Exception {
        //1.validate username/password

        //generate token
        String token;
        Map<String, String> resultMap = new HashMap<>();

        try {
            User retrievedUser = userService.getUserByCredential(user.getEmail(), user.getPassword());
            if (retrievedUser == null) {
                resultMap.put("msg", errorMsg);
                return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(resultMap);
            }

            token = jwtService.generateToken(retrievedUser);
            resultMap.put("token", token);
        }catch (Exception e){
            String msg = e.getMessage();
            if(msg == null){
                msg = "BAD REQUEST!";
            }
            logger.error(msg);
            resultMap.put("msg", msg);
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resultMap);
        }

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(resultMap);
    }

}
