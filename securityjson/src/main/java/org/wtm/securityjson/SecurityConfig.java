package org.wtm.securityjson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and().csrf().disable();
        http.addFilterAt(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    MyAuthFilter myAuthenticationFilter() throws Exception {
        MyAuthFilter filter = new MyAuthFilter();
        //成功的回调
        filter.setFilterProcessesUrl("/success");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}