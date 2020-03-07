package org.wtm.shiro2;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    @Bean
    Realm realm(){
        TextConfigurationRealm realm = new TextConfigurationRealm();
        //在内存中定义了两个用户
        realm.setUserDefinitions("user=123,user \n admin=123,admin");
        //设置权限
        realm.setRoleDefinitions("admin=read,write \n user=read");
        return realm;
    }


    //拦截规则
    @Bean
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        defaultShiroFilterChainDefinition.addPathDefinition("/doLogin","anon");
        defaultShiroFilterChainDefinition.addPathDefinition("/**","authc");
        return defaultShiroFilterChainDefinition;
    }

}
