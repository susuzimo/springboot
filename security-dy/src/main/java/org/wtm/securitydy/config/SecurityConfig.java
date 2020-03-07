package org.wtm.securitydy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.wtm.securitydy.service.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserService userService;

    @Autowired
    MyFilter myFilter;

    @Autowired
    MyAccessDecisionManager myAccessDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

//    //角色继承
//    @Bean
//    RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        String hierarchy = "ROLE_dba > ROLE_admin \n ROLE_admin > ROLE_user";
//        roleHierarchy.setHierarchy(hierarchy);
//        return roleHierarchy;
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                   @Override
                   public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        o.setSecurityMetadataSource(myFilter);
                       return o;
                   }
               })
               .and()
               .formLogin()
               .permitAll()
               .and()
               .csrf()
               .disable();
    }
}
