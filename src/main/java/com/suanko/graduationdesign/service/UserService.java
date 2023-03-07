package com.suanko.graduationdesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suanko.graduationdesign.entity.User;
import org.apache.ibatis.annotations.Results;

import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    User login(String username, String password);

    void saveUserRole(Integer uid, Integer[] ids);


}
