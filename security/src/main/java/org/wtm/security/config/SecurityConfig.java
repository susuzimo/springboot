package org.wtm.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//单个
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 基于内存的认证
        auth.inMemoryAuthentication()
                .withUser("user").password("123456").roles("user")
                .and()
                .withUser("admin").password("123456").roles("admin");
    }


    //配置拦截规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启配置
        http.authorizeRequests()
                .antMatchers("/admin/hello").hasRole("admin")
//                .antMatchers("/user/*").hasAnyRole("admin","user")
                .antMatchers("/user/*").access("hasAnyRole('user','admin')")
                //其他路径只要登录就能访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/doLogin")  //处理登录请求的地址
                .loginPage("/login")                //登录页地址
                .usernameParameter("username")      //修改默认的登录名称
                .passwordParameter("password")       //修改默认的登录名称
                .successHandler(new AuthenticationSuccessHandler() {
                    //登录成功之后的操作
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        Map<String,Object> map=new HashMap<>();
                        map.put("status","200");
                        //登录成功的信息msg     登录成功的对象Authentication
                        map.put("msg",authentication.getPrincipal());
                        writer.write(new ObjectMapper().writeValueAsString(map));
                        writer.flush();
                        writer.close();

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    //登录失败
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        Map<String,Object> map=new HashMap<>();
                        map.put("status","401");
                        if(e instanceof LockedException){
                            map.put("msg","账号被锁定,登录失败");
                        }else if(e instanceof BadCredentialsException){
                            map.put("msg","用户名或者密码输入错误，登录失败");
                        }else if(e instanceof DisabledException){
                            map.put("msg","账户被禁用，登录失败");
                        }else if(e instanceof AccountExpiredException){
                            map.put("msg","账户过期，登录失败");
                        }else if(e instanceof CredentialsExpiredException){
                            map.put("msg","密码过期，登录失败");
                        }else{
                            map.put("msg","登录失败");

                        }
                        writer.write(new ObjectMapper().writeValueAsString(map));
                        writer.flush();
                        writer.close();
                    }
                })
                .permitAll()//放行登录的请求
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    //注销成功
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        resp.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = resp.getWriter();
                        Map<String,Object> map=new HashMap<>();
                        map.put("status","200");
                        //登录成功的信息msg     登录成功的对象Authentication
                        map.put("msg","注销成功");
                        writer.write(new ObjectMapper().writeValueAsString(map));
                        writer.flush();
                        writer.close();

                    }
                })
                .and()
                .csrf()
                .disable()
        ;
    }
}
