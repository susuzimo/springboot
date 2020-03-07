package org.wtm.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//配置多个httpSecurity
@Configuration
//方法安全
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MultiHttpSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //用户公用的
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 基于内存的认证
        auth.inMemoryAuthentication()
                .withUser("user").password("$2a$10$K6lUMySRRMazEuskU53RLeULO/hX5OL6qF2rYOirJVN92CaQA/FoG").roles("user")
                .and()
                .withUser("admin").password("$2a$10$bPcx0oQUEkVDna2fyi74.e1xDYBGlkkfHduGWGkE2gRwD0HM9dLb6").roles("admin");
    }




    //优先级 数字越小优先级越大
    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //只有这个 /admin/** 需要admin角色
            http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasAnyRole("admin");
        }
    }


    @Configuration
    public static class OtherSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //只有这个 /admin/** 需要admin角色
            http.authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/doLogin")
                    .permitAll()
                    .and()
                    .csrf()
                    .disable();

        }
    }


}
