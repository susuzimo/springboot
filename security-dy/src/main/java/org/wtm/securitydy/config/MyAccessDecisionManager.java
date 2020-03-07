package org.wtm.securitydy.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /*
    Authentication  存放当前登录信息
    Object          获取当前请求对象
    Collection<ConfigAttribute>   访问路径需要哪些角色
     */

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            if("ROLE_login".equals(configAttribute.getAttribute())){
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("非法请求");
                }else{
                    return;
                }
            }

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            //遍历自己拥有的角色
            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(configAttribute.getAttribute())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("非法请求");

    }


    //是否支持这种方式
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
