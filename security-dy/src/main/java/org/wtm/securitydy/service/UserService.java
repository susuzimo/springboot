package org.wtm.securitydy.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wtm.securitydy.bean.User;
import org.wtm.securitydy.mapper.UserMapper;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userMapper.loadUserByUsername(username);
        //自动比较密码
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        user.setRoles(userMapper.selectById(user.getId()));
        return user;
    }



}
