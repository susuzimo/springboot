package org.wtm.shiro1.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/login")
    public String login(){
        return  "please login";
    }

    @GetMapping("/doLogin")
    public void doLogin(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(username,password));
            System.out.println("success");
        }catch (AuthenticationException e){
            System.out.println("fail");
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return  "hello shiro";
    }

}
