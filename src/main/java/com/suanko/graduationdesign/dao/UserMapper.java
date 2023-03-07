package com.suanko.graduationdesign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suanko.graduationdesign.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{username} and password=#{password}")
    User login(String username,String password);

}
