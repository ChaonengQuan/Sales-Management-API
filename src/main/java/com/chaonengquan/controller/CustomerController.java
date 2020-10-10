package com.chaonengquan.controller;

import com.chaonengquan.model.Customer;
import com.chaonengquan.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController(value = "/customer")
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    //@RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> hello() {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("msg", "Hello from server!");
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(resultMap);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer add(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@RequestBody Customer customer){
        boolean status = customerService.delete(customer);
        if(status == true){     //for readability
            return "Success!";
        }else{
            return "Failed!";
        }
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)        //convert return object to JSON
    public  List<Customer> getAllCustomer() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Customer> allCustomer = customerService.getAllCustomer();
     //   for (Customer c : allCustomer) {
       //     stringBuilder.append(c.toString());
       // }
        return allCustomer;
    }

    //use request param
    @GetMapping(value = "/{Id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer getFirstCustomer(@PathVariable("Id") long id) {
        return customerService.getById(id);
    }



}
