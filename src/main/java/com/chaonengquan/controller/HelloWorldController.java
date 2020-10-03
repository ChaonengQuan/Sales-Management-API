//package com.chaonengquan.controller;
//
//import com.chaonengquan.model.Customer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//
////@RestController
////@RequestMapping(value = "/world")
//public class HelloWorldController {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
//    //@RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String hello() {
//        return "Hello from GET";
//    }
//
//    @PostMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
//    //@RequestMapping(value = "/hello", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String helloPost() {
//        return "Hello from Post";
//    }
//
//    @GetMapping(value = "/hello/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String greetingWithHello(@PathVariable("name") String name) {
//
//        return "grettings!" + name;
//    }
//
//
//    @GetMapping(value = "/helloWorld", params = {"name"}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String greetingWithHelloWithSingleRequestParam(@RequestParam("name") String name123) {
//        return "grettings! " + name123;
//    }
//
//    @GetMapping(value = "/helloWorld", params = {"name", "location"}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String greetingWithHelloWithTwoRequestParam(@RequestParam("name") String name123, @RequestParam("location") String location) {
//        return "grettings! " + name123 + " from " + location;
//    }
//
//
//    @PostMapping(value = "/hi/customer", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Customer createCustomer(@RequestBody Customer customer) {
//        customer.setId(345);
//        return customer;
//    }
//
//
//    @PutMapping(value = "/hi/customer", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Customer updateCustomer(@RequestBody Customer customer) {
//        customer.setId(345);
//        customer.setLastName("update_test");
//        return customer;
//    }
//
//    //@Patch
//
//    @DeleteMapping(value = "delete/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String deleteCustomer(@PathVariable("name") String name) {
//        return "Delete looks good! " + name + " is deleted!";
//
//    }
//
//
//    @GetMapping("/headerTest")
//    public String headerTest(@RequestHeader("myItem") String language) {
//        return "The item 'myItem' has a value " + language;
//    }
//
//}
