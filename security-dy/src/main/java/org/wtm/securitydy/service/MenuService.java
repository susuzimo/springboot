package org.wtm.securitydy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wtm.securitydy.bean.Menu;
import org.wtm.securitydy.mapper.MenuMapper;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

   public List<Menu> getMenus(){
        return menuMapper.getMenus();
    }
}
