package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.MenuMapper;
import com.suanko.graduationdesign.entity.Menu;
import com.suanko.graduationdesign.service.MenuService;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
}
