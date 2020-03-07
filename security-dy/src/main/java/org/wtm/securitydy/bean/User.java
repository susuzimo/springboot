package org.wtm.securitydy.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails{
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    List<Role> roles;


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //同上
/*    public Boolean getEnabled() {
        return enabled;
    }*/

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    //因为底下有了 不能重复
/*    public Boolean getLocked() {
        return locked;
    }*/

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }


    //返回用户的所有角色
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Role role:roles){
            //如果数据库里带ROLE_就不用加了
            authorities.add(new SimpleGrantedAuthority(role.getName()));

        }
        return authorities;
    }

    //因为表里没有这个字段 所以默认未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //因为表里有这个字段
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    //密码是否未过期 也没有所以写死
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //有这个字段
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", roles=" + roles +
                '}';
    }
}
