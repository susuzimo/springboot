package org.wtm.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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
                .formLogin().loginProcessingUrl("/doLogin")  //处理登录请求的地址
                .permitAll()//放行登录的请求
                .and()
                .csrf()
                .disable()
        ;
    }
}
