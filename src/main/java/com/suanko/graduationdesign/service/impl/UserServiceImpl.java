package com.suanko.graduationdesign.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suanko.graduationdesign.dao.RoleMapper;
import com.suanko.graduationdesign.dao.UserMapper;
import com.suanko.graduationdesign.entity.User;
import com.suanko.graduationdesign.service.UserService;
import org.apache.ibatis.annotations.Results;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        roleMapper.deleteRoleUserByUid(uid);
        if (ids != null && ids.length > 0) {
            for (Integer rid : ids) {
                roleMapper.saveUserRole(uid, rid);
            }
        }
    }


}
