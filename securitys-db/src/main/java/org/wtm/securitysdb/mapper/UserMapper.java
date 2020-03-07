package org.wtm.securitysdb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wtm.securitysdb.bean.Role;
import org.wtm.securitysdb.bean.User;

import java.util.List;

@Mapper
public interface UserMapper {

    User loadUserByUsername(String username);

    List<Role> selectById(Integer id);
}
