package org.wtm.securitydy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.wtm.securitydy.bean.Menu;
import org.wtm.securitydy.bean.Role;
import org.wtm.securitydy.service.MenuService;

import java.util.Collection;
import java.util.List;

//过滤器
//根据访问的地址，分析需要那个角色

@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {

    //路径匹配符
    AntPathMatcher pathMatcher= new AntPathMatcher();

    @Autowired
    MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuService.getMenus();
        for(Menu menu:menus){
            if(pathMatcher.match(menu.getPattern(),requestUrl)){
                List<Role> roles = menu.getRoles();
                String[] rolesStr=new String[roles.size()];
                for(int i=0;i<roles.size();i++){
                    rolesStr[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(rolesStr);
            }
        }
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
