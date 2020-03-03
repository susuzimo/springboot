package org.wtm.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public  String hello(){
        return "hello security";
    }


    @GetMapping("admin/hello")
    public  String hellos(){
        return "hello admin";
    }


    @GetMapping("user/hello")
    public  String helloS(){
        return "hello user";
    }

}
