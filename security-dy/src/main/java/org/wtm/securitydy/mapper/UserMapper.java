package org.wtm.securitydy.mapper;



import org.wtm.securitydy.bean.Role;
import org.wtm.securitydy.bean.User;

import java.util.List;


public interface UserMapper {

    User loadUserByUsername(String username);

    List<Role> selectById(Integer id);
}
