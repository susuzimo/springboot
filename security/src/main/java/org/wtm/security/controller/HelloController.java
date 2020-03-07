package org.wtm.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wtm.security.service.MethodService;

@RestController
public class HelloController {

    @Autowired
    MethodService methodService;

    @GetMapping("/hello1")
    public  String hello1(){
        String admin = methodService.admin();
        return admin;
    }

    @GetMapping("/hello2")
    public  String hello2(){
        String user = methodService.user();
        return user;
    }

    @GetMapping("/hello3")
    public  String hello3(){
        return "hello security";
    }



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


    @GetMapping("/login")
    public  String login(){
        return "please login";
    }

}
