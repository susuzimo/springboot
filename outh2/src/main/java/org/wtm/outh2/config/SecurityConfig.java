package org.wtm.outh2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 基于内存的认证
        auth.inMemoryAuthentication()
                .withUser("user").password("$2a$10$/t6Xej2quLL61UsDpyhv/.x3d/0ZowiBZrvzXBMPSV55VNFx26AZy").roles("user")
                .and()
                .withUser("admin").password("$2a$10$/t6Xej2quLL61UsDpyhv/.x3d/0ZowiBZrvzXBMPSV55VNFx26AZy").roles("admin");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/oauth/**")
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .and().csrf().disable();
    }
}
